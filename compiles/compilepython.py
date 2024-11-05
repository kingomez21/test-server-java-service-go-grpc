import os

list_proto_files = [
    "TaskService.proto",
    "UserService.proto"
]

ruta_protos = "../server/src/main/proto"

ruta_python = "../services/reports/services"

abs_path_protos = os.path.abspath(ruta_protos)

abs_path_python = os.path.abspath(ruta_python)

for names in range(len(list_proto_files)):
    abs_file_name = os.path.join(abs_path_protos, list_proto_files[names])
    #print(f'protoc --proto_path={abs_path_protos} --python_out={abs_path_python} --grpc_python_out={abs_path_python} {abs_file_name}')
    os.system(f'python -m grpc_tools.protoc --proto_path={abs_path_protos} --python_out={abs_path_python} --pyi_out={abs_path_python} --grpc_python_out={abs_path_python} {abs_file_name}')
    