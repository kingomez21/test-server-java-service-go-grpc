import services.UserService_pb2 as UserServices
import services.UserService_pb2_grpc as UserServicesGrpc

user_data = [
    {
        "id": "1",
        "firstname": "pedro",
        "lastname": "infante",
        "email": "infante@gmail.com",
        "address": "Villa Ariel"
    },
    {
        "id": "2",
        "firstname": "Gabriel",
        "lastname": "Garcia Marquez",
        "email": "garciamarquez@gmail.com",
        "address": "Villa Ariel 2"
    }
]

class UserController(UserServicesGrpc.UserServiceServicer):

    def GetUsers(self, request, context):

        global user_data
        data = []

        for d in user_data:
            data.append(UserServices.User(
                id=d.get("id"),
                firstname=d.get("firstname"),
                lastname=d.get("lastname"),
                email=d.get("email"),
                address=d.get("address")
            ))

        return UserServices.ListUser(users=user_data)

