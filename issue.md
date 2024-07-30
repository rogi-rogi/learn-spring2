# CICD.yml의 초기 배포시 주의사항

blue를 생성하고 green을 삭제하는 부분에서 에러가 발생함.

yml 수정 또는 수동 초기 배포 필요 -> 컨테이너 명 확인 후 삭제하는 것으로 수정 완료

# AWS ec2 프리티어 메모리 제한

두 컨테이너가 동시에 올라가는 순간 필요 메모리를 무한대로 요구하고 결국 ec2서버가 매우 느려지는 현상이 발생.

그린과 블루를 각각 256mb, mysql을 512로 설정함.

```bash
root@ip-172-31-13-145:/home/ubuntu# cat docker-compose-blue.yml
version: '3.8'
services:
  blue:
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 256M
    image: rogi2/live_server:latest
    container_name: blue
    ports:
      - "8080:8080"
    environment:
      - PROFILES=blue
      - ENV=blue

  main-db:
    deploy:
      resources:
        limits:
          memory: 512M
    image: mysql:8.0
    container_name: main-db
    environment:
      MYSQL_ROOT_PASSWORD: admin
      MYSQL_DATABASE: learn
    ports:
      - "3306:3306"
    volumes:
      - db-data:/var/lib/mysql

volumes:
  db-data:

networks:
  default:
    external:
      name: shared-network
```
      
```bash
root@ip-172-31-13-145:/home/ubuntu# cat docker-compose-green.yml
version: '3.8'
services:
  green:
    deploy:
      resources:
        limits:
          cpus: '0.5'
          memory: 256M
    image: rogi2/live_server:latest
    container_name: green
    ports:
      - "8081:8081"
    environment:
      - PROFILES=green
      - ENV=green

networks:
  default:
    external:
      name: shared-network
```
