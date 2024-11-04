package main

import (
	"log"
	"net"

	"github.com/kingomez21/task/controllers"
	"github.com/kingomez21/task/database"
	"github.com/kingomez21/task/taskservice"
	"github.com/kingomez21/task/userservice"
	"google.golang.org/grpc"
)

func main() {

	list, err := net.Listen("tcp", ":50051")

	database.ConectionDB()

	if err != nil {
		log.Fatal("Failed server")
	}

	s := grpc.NewServer()

	userservice.RegisterUserServiceServer(s, &controllers.UserServer{})
	taskservice.RegisterTaskServiceServer(s, &controllers.TaskServer{})

	log.Println("Server is running on port 50051...")

	if err := s.Serve(list); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}

}
