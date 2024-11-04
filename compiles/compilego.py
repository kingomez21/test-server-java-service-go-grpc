import os

list_proto_files = [
    "TaskService.proto",
    "UserService.proto"
]

ruta_protos = "../server/src/main/proto"

ruta_go = "../services"

abs_path_protos = os.path.abspath(ruta_protos)

abs_path_go = os.path.abspath(ruta_go)

for names in range(len(list_proto_files)):
    abs_file_name = os.path.join(abs_path_protos, list_proto_files[names])
    #print(f'protoc --proto_path={abs_file_name} --go_out={abs_path_go} --go-grpc_out={abs_path_go} {abs_file_name}')
    os.system(f'protoc --proto_path={abs_path_protos} --go_out={abs_path_go} --go-grpc_out={abs_path_go} {abs_file_name}')
    

#protoc 
# -I=C:\DocumentsJuan\testgRPC\crud_grpc\server\src\main\proto 
# --go_out=. 
# --go-grpc_out=. C:\DocumentsJuan\testgRPC\crud_grpc\server\src\main\proto\TaskService.proto

#print(os.path.join(""))