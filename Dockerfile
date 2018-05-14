# Dockerfile for POLI coin daemon

#
# Daemon executable builder
#
FROM ubuntu:16.04 as builder

RUN apt-get -y update; \
    apt-get -y install \
        build-essential \
        git \
        libboost-filesystem-dev \
        libboost-program-options-dev \
        libboost-system-dev \
        libboost-thread-dev \
        libssl-dev \
        libdb++-dev \
        libminiupnpc-dev \
        libqrencode-dev

RUN git clone https://github.com/Polisure/POLI /tmp/poli \
 && cd /tmp/poli/src \
 && chmod +x leveldb/build_detect_platform \
 && make -f makefile.unix


#
# Final Docker image
#
FROM ubuntu:16.04
LABEL description="Poli Daemon"
EXPOSE 3333 3334 9071 9072 18444 18445

COPY --from=builder /tmp/poli/src/POLId /usr/local/bin/polid
COPY .docker /

RUN apt-get -y update \
 && apt-get -y install \
        gettext \
        libboost-filesystem-dev \
        libboost-program-options-dev \
        libboost-system-dev \
        libboost-thread-dev \
        libssl-dev \
        libdb++-dev \
        libminiupnpc-dev \
        libqrencode-dev \
        netcat \
        pwgen \
 && groupadd --gid 1000 poli \
 && useradd --uid 1000 --gid poli --shell /bin/false --create-home poli \
 && chmod +x /usr/local/bin/notifier \
 && chmod +x /usr/local/bin/entrypoint

USER poli
WORKDIR /home/poli

RUN mkdir -p /home/poli/.POLI

ENTRYPOINT [ "/usr/local/bin/entrypoint" ]
CMD [ "polid", "-printtoconsole" ]
