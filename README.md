Este proyecto corre con pgvector(en este caso con docker) y ollama.

1. Levantar pgvector en Docker:

~~~
docker run -d --name pgvector -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=admin -e POSTGRES_DB=spring_ai_rag pgvector/pgvector:pg16-trixie
~~~

2. Ingresar al contenedor y base de datos:

~~~
docker exec -it pgvector psql -U admin -d spring_ai_rag
~~~

3. Instalar extensiones necesarias:

~~~
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
~~~

4. Asegurarse de que se crearon las extensiones:

~~~
SELECT extname from pg_extension;
~~~

5. Ya con Ollama instalado descargamos el modelo que vamos a utilizar:

~~~
ollama pull nomic-embed-text
~~~

6. Para ver los modelos que tenemos disponibles:

~~~
ollama list
~~~
