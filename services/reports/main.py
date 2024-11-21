import os
import sys
abs_service = os.path.abspath("./services")
sys.path.append(abs_service)

import grpc
from concurrent import futures
import services.UserService_pb2 as UserServices
import services.UserService_pb2_grpc as UserServicesGrpc
from UserController import UserController

path_cert = '../../certs'
abs_path_cert = os.path.abspath(path_cert)
cert = os.path.join(abs_path_cert, "cert.crt")
key = os.path.join(abs_path_cert, "key.pem")

class LoggingInterceptor(grpc.ServerInterceptor):
    def intercept_service(self, continuation, handler_call_details):
        print(f"Protocolo: {handler_call_details.invocation_metadata}")
        return continuation(handler_call_details)

def main():
    try:
        with open(cert, "rb") as cert_file, open(key, "rb") as key_file:
            server_credentials = grpc.ssl_server_credentials(
                [(key_file.read(), cert_file.read())]
            )
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10), interceptors=[LoggingInterceptor()])
        UserServicesGrpc.add_UserServiceServicer_to_server(UserController(), server)
        server.add_secure_port("[::]:50052", server_credentials)
        server.start()
        print("server on port 50052")
        server.wait_for_termination()
    except KeyboardInterrupt:
        print("shutdown server")


if __name__ == "__main__":
    main()