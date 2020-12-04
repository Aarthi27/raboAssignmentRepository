# RABO BANK ASSIGNMENT

## REST API

The REST API to the assignment is described below.

## Get list of Person

### Request

`GET /persondetails/persons`

   http://localhost:8080/persondetails/persons
   
### Response

    HTTP/1.1 200 OK
    
	[
    {
        "id": 1,
        "firstname": "Aarthi",
        "lastname": "N",
        "dob": "11/08/2001",
        "address": "chennai"
    },
    {
        "id": 2,
        "firstname": "Lavanya",
        "lastname": "N",
        "dob": "11/08/1989",
        "address": "Vellore"
    }
]

## Create a new Person

### Request

`POST /persondetails/persons`

  http://localhost:8080/persondetails/persons
  
  ###Request Body
  
  [
    {
       "firstname": "Aarthi",
        "lastname": "N",
        "dob": "11/08/2001",
        "address": "chennai"
       
    }   
]

### Response

    'Record Saved Successfully'
	
	Will gett error message if we try to add person who already exists.
	
	'Entered Record already available. Try saving Unique Name.'

## Get a specific Person

### Request

`GET /persondetails/person/id`

   http://localhost:8080/persondetails/person/1

### Response

    HTTP/1.1 200 OK
    

    {
    "id": 1,
    "firstname": "Aarthi",
    "lastname": "N",
    "dob": "11/08/2001",
    "address": "chennai"
   }

## Get a non-existent Person

### Request

`GET /persondetails/person/id`

    http://localhost:8080/persondetails/person/3

### Response

    HTTP/1.1 404 Not Found
    {"status":404,"reason":"Not found"}
	
	Will Get below message
	
	'Searched Record not found with Id 3'

## Get person with name

### Request

`POST /persondetails/person?firstName=&lastName=`

    http://localhost:8080/persondetails/person?firstName=Aarthi&lastName=
	http://localhost:8080/persondetails/person?firstName=Aarthi&lastName=N
	http://localhost:8080/persondetails/person?firstName=&lastName=N
	http://localhost:8080/persondetails/person?firstName=&lastName=

### Response

 HTTP/1.1 200 OK
    [
    {
        "id": 1,
        "firstname": "Aarthi",
        "lastname": "N",
        "dob": "11/08/2001",
        "address": "chennai"
    }
]

## Get list of Person with Pet Mapped

### Request

`GET /persondetails/persons`

   http://localhost:8080/persondetails/persons

### Response

    HTTP/1.1 200 OK
    [
    {
        "id": 1,
        "firstname": "Aarthi",
        "lastname": "N",
        "dob": "11/08/2001",
        "address": "chennai"
    },
    {
        "id": 2,
        "firstname": "Lavanya",
        "lastname": "N",
        "dob": "11/08/1989",
        "address": "Vellore",
        "petList": [
            {
                "pet_id": 1,
                "name": "Lion",
                "age": 9
            }
        ]
    }
]

## Change address of a person

### Request

`PATCH /persondetails/person

    http://localhost:8080/persondetails/person
	
	### Request Body
	{
	"id":1,
	"address":"salem"
}

### Response

    HTTP/1.1 200 OK
   

## Delete a Person

### Request

`DELETE /persondetails/person/id`

http://localhost:8080/persondetails/person/1

### Response

Deleted record successfully

## Try to delete same Person again

### Request

`DELETE /persondetails/person/id`

http://localhost:8080/persondetails/person/1

### Response

    HTTP/1.1 404 Not Found
	
	####ERROR MESSAGE
    Searched Record not found with Id 1

## Delete all Persons

### Request

`DELETE /persondetails/person`

http://localhost:8080/persondetails/person

### Response

Deleted all record successfully



## Get list of Pet

### Request

`GET /petdetails/pets`

   http://localhost:8080/petdetails/pets
   
### Response

    HTTP/1.1 200 OK
    [
    {
        "pet_id": 1,
        "name": "Lion",
        "age": 9
    },
    {
        "pet_id": 2,
        "name": "Cat",
        "age": 5
    }
]

## Create a new Pet

### Request

`POST /petdetails/pet`

  http://localhost:8080/petdetails/pet
  
  ###Request Body
  
  [{
        "name": "Lion",
        "age": 9
    },
    {
        "name": "Cat",
        "age": 5
    }
    ]

### Response

    'Record Saved Successfully'
	
	
## Get a specific Pet

### Request

`GET /petdetails/pet/id`

   http://localhost:8080/petdetails/pet/1

### Response

    HTTP/1.1 200 OK
    
{
    "pet_id": 1,
    "name": "Lion",
    "age": 9
}

## Get a non-existent Person

### Request

`GET /petdetails/pet/id`

    http://localhost:8080/petdetails/pet/3

### Response

    HTTP/1.1 404 Not Found
    {"status":404,"reason":"Not found"}
	
	####Error message
	
	'Searched Record not found with Id 3'


## Update a pet

### Request

`PUT /petdetails/pet`

    http://localhost:8080/petdetails/pet
	
	### Request Body
	{
	"pet_id":1,
        "name": "Doggy",
        "age": 3
    }

### Response

    HTTP/1.1 200 OK
   
   Record Updated Successfully

## Delete a Pet

### Request

`DELETE petdetails/pet/1`

http://localhost:8080/petdetails/pet/1

### Response

Deleted record successfully

## Try to delete same Person again

### Request

`DELETE /petdetails/pet/id`

http://localhost:8080/petdetails/pet/1

### Response

    HTTP/1.1 404 Not Found
	
	####ERROR MESSAGE
    Searched Record not found with Id 1

## Delete all Persons

### Request

`DELETE /persondetails/person`

http://localhost:8080/persondetails/person

### Response

Deleted all record successfully



## Map Pet to a person

### Request

`POST /persondetails/personpet`

  http://localhost:8080/persondetails/personpet
  
  ###Request Body
  
  {
	"person_id":2,
	"pet_id":1
    
    }

### Response

    'Person Mapped to Pet Successfully'
	

## Check Person Pet Mapping

### Request

`GET /persondetails/persons`

   http://localhost:8080/persondetails/persons

### Response

    HTTP/1.1 200 OK
    [
    {
        "id": 2,
        "firstname": "Lavanya",
        "lastname": "N",
        "dob": "11/08/1989",
        "address": "Vellore",
        "petList": [
            {
                "pet_id": 1,
                "name": "Lion",
                "age": 9
            },
            {
                "pet_id": 2,
                "name": "Cat",
                "age": 5
            }
        ]
    }
]

## Delete a pet and check the list.

### Request

`GET /persondetails/persons`

   http://localhost:8080/persondetails/persons

### Response

    HTTP/1.1 200 OK
   [
    {
        "id": 2,
        "firstname": "Lavanya",
        "lastname": "N",
        "dob": "11/08/1989",
        "address": "Vellore",
        "petList": [
            {
                "pet_id": 1,
                "name": "Lion",
                "age": 9
            }
        ]
    }
]
