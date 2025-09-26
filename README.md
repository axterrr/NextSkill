
## 1. Build the `.jar` with Docker

We use a multi-stage Dockerfile (`Dockerfile.build`) with the `--output` option to extract the build artifact directly to the host, without running a container.

```bash
docker build -f Dockerfile.build --output ./build/libs .
```

---
## 2. Create run image

```bash
docker build -t springboot-next-skill -f Dockerfile.run .
```

## 3. Run container

```bash
docker run --env-file .env -p 8080:8080 springboot-next-skill 
```