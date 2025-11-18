db = db.getSiblingDB('persona_db');

db.persona.insertMany([
	{
		"_id": 123456789,
		"nombre": "Pepe",
		"apellido": "Perez",
		"genero": "M",
		"edad": 30,
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": 987654321,
		"nombre": "Pepito",
		"apellido": "Perez",
		"genero": "M",
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": 321654987,
		"nombre": "Pepa",
		"apellido": "Juarez",
		"genero": "F",
		"edad": 30,
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": 147258369,
		"nombre": "Pepita",
		"apellido": "Juarez",
		"genero": "F",
		"edad": 10,
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	},
	{
		"_id": 963852741,
		"nombre": "Fede",
		"apellido": "Perez",
		"genero": "M",
		"edad": 18,
		"_class": "co.edu.javeriana.as.personapp.mongo.document.PersonaDocument"
	}
], { ordered: false })
