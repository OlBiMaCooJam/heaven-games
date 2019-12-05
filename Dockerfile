FROM openjdk:8

MAINTAINER smjeon <oeeen3@gmail.com>

VOLUME ~/deploy/heaven

COPY ./start-server.sh /usr/local/bin
RUN ln -s /usr/local/bin/start-server.sh ~/start-server.sh
CMD ["start-server.sh"]