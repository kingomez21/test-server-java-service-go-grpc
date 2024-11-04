package controllers

import (
	"context"
	"log"

	"github.com/kingomez21/task/database"
	"github.com/kingomez21/task/userservice"
)

type UserServer struct {
	userservice.UnimplementedUserServiceServer
}

func (s *UserServer) GetUsers(ctx context.Context, req *userservice.Empty) (*userservice.ListUser, error) {

	var list_user []*userservice.User

	rows, err := database.DB.Query("CALL GetUsuarios()")

	if err != nil {
		log.Fatal("error al ejecutar la consulta")
	}
	defer rows.Close()

	for rows.Next() {
		var user userservice.User
		//var ID, firstname, lastname, email, address string
		if err := rows.Scan(&user.Id, &user.Firstname, &user.Lastname, &user.Email, &user.Address); err != nil {
			log.Fatal("Error al obtener las filas")
		}
		list_user = append(list_user, &user)
	}

	return &userservice.ListUser{Users: list_user}, nil

}

func (s *UserServer) CreateUser(ctx context.Context, req *userservice.User) (*userservice.Response, error) {
	_, err := database.DB.ExecContext(ctx, "CALL InsertUsuario(?,?,?,?)", req.GetFirstname(), req.GetLastname(), req.GetEmail(), req.GetAddress())

	if err != nil {
		log.Printf("Error al insertar el registro")
		return &userservice.Response{Message: "Ha ocurrido un error inesperado"}, err
	}

	return &userservice.Response{Message: "Guardado con exito"}, nil
}
