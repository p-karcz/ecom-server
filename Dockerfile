FROM openjdk:8-jdk
RUN mkdir /app
COPY ./build/install/ecom-server/ /app/
WORKDIR /app/bin
CMD ["./ecom-server"]
