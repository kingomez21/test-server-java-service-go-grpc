package database

import (
	"database/sql"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

var DB *sql.DB

func ConectionDB() {

	host := "localhost"
	user := "root"
	password := ""
	dbname := "db_test_grpc"

	dsn := user + ":" + password + "@tcp(" + host + ")/" + dbname

	var err error

	DB, err = sql.Open("mysql", dsn)

	if err != nil {
		log.Fatal("Error con la conexion a la base de datos", err)
	}

	if err = DB.Ping(); err != nil {
		log.Fatal("Error al hacer ping ", err)
	}

	log.Println("Conexion a la base de datos exitosa")

}
