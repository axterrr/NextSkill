
## 1. Build the `.jar` with Docker

We use a multi-stage Dockerfile (`Dockerfile.build`) with the `--output` option to extract the build artifact directly to the host, without running a container.

```bash
docker build -f ./Dockerfile.build -t axterrr/next-skill-build --output ./build/libs .
```

---
## 2. Create run image

```bash
docker build -f ./Dockerfile.run -t axterrr/next-skill-run .
```

## 3. Run container

```bash
docker run --name next-skill-run --env-file .env -p 8080:8080 axterrr/next-skill-run 
```

---
## 4. Push image to DickerHub

```bash
docker push axterrr/next-skill-run
```
