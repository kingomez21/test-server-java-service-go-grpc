package controllers

import (
	"context"
	"log"

	"github.com/kingomez21/task/database"
	"github.com/kingomez21/task/taskservice"
)

type TaskServer struct {
	taskservice.UnimplementedTaskServiceServer
}

func (s *TaskServer) GetTask(ctx context.Context, req *taskservice.Empty) (*taskservice.ListTask, error) {

	var list_task []*taskservice.Task

	rows, err := database.DB.Query("CALL GetTask")

	if err != nil {
		log.Fatal("Error al obtener las tareas")
	}
	defer rows.Close()

	for rows.Next() {
		var task taskservice.Task

		if err := rows.Scan(&task.Id, &task.Name, &task.Description, &task.Date, &task.Done); err != nil {
			log.Fatal("Error al obtener las filas")
		}

		list_task = append(list_task, &task)

	}

	return &taskservice.ListTask{Tasks: list_task}, nil
}

func (s *TaskServer) CreateTask(ctx context.Context, req *taskservice.TaskCreate) (*taskservice.Response, error) {

	_, err := database.DB.ExecContext(ctx, "CALL InsertTask(?,?,?,?)",
		req.GetName(), req.GetDescription(), req.GetDate(), req.GetDone())

	if err != nil {
		log.Fatal("Error al insertar el registro", err)
		return &taskservice.Response{Message: "Ocurrio un error inesperado"}, nil
	}

	return &taskservice.Response{Message: "Creado con exito"}, nil
}
