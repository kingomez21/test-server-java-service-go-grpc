package main

import (
	"context"
	"log"
	"net"

	"github.com/kingomez21/task/database"
	"github.com/kingomez21/task/taskservice"
	"google.golang.org/grpc"
)

type Server struct {
	taskservice.UnimplementedHelloServer
}

func (s *Server) SayHello(ctx context.Context, req *taskservice.Request) (*taskservice.Response, error) {
	return &taskservice.Response{Message: "Hola, otra vez desde go y java " + req.GetName()}, nil

}

func (s *Server) GetUsers(ctx context.Context, req *taskservice.Empty) (*taskservice.ListUser, error) {

	var list_user []*taskservice.User

	rows, err := database.DB.Query("CALL GetUsuarios()")

	if err != nil {
		log.Fatal("error al ejecutar la consulta")
	}
	defer rows.Close()

	for rows.Next() {
		var user taskservice.User
		//var ID, firstname, lastname, email, address string
		if err := rows.Scan(&user.Id, &user.Firstname, &user.Lastname, &user.Email, &user.Address); err != nil {
			log.Fatal("Error al obtener las filas")
		}
		list_user = append(list_user, &user)
	}

	return &taskservice.ListUser{Users: list_user}, nil

}

func (s *Server) CreateUser(ctx context.Context, req *taskservice.User) (*taskservice.Response, error) {
	_, err := database.DB.ExecContext(ctx, "CALL InsertUsuario(?,?,?,?)", req.GetFirstname(), req.GetLastname(), req.GetEmail(), req.GetAddress())

	if err != nil {
		log.Printf("Error al insertar el registro")
		return &taskservice.Response{Message: "Ha ocurrido un error inesperado"}, err
	}

	return &taskservice.Response{Message: "Guardado con exito"}, nil
}

func main() {

	list, err := net.Listen("tcp", ":50051")

	database.ConectionDB()

	if err != nil {
		log.Fatal("Failed server")
	}

	s := grpc.NewServer()

	taskservice.RegisterHelloServer(s, &Server{})

	log.Println("Server is running on port 50051...")

	if err := s.Serve(list); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}

}
