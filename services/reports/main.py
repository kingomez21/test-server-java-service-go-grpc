import os
import sys
abs_service = os.path.abspath("./services")
sys.path.append(abs_service)

import grpc
from concurrent import futures
import services.UserService_pb2 as UserServices
import services.UserService_pb2_grpc as UserServicesGrpc
from UserController import UserController


def main():
    try:
        server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))
        UserServicesGrpc.add_UserServiceServicer_to_server(UserController(), server)
        server.add_insecure_port("[::]:50052")
        server.start()
        print("server on port 50052")
        server.wait_for_termination()
    except KeyboardInterrupt:
        print("shutdown server")


if __name__ == "__main__":
    main()