# Matthiola-TraderGateway

[![Build Status](https://travis-ci.com/ljw9609/Trader.svg?token=rZsycNAAqukSyU9AujYH&branch=master)](https://travis-ci.com/ljw9609/Trader)
![language](https://img.shields.io/badge/language-java-red.svg)

The Trader Gateway of *Distributed Commodities OTC Electronic Trading System*, instructed by *Morgan Stanley* and *SJTU SE*.

## Architecture

Communication with Web Client:
+ HTTP
+ Websocket

Communication with Broker Gateway:
+ Websocket: Get **real-time** information(e.g. futures' orderbook and trade history)
+ FIX: Send order and cancel order
+ HTTP: Get normal query information(e.g. personal orders and trade history)

### Overall Architecture

![architecture](https://raw.githubusercontent.com/ljw9609/markdown-pictures/master/gatewayarchitecture.png?token=ATNvW2LapNgKNG34AGYUbdVkx_SWoAtGks5bJHh4wA%3D%3D)

### Websocket Architecture

![websocker](https://raw.githubusercontent.com/ljw9609/markdown-pictures/master/websocket.png?token=ATNvWxgTQdCkZSTlN3bC7865va2eBcxeks5bJHpswA%3D%3D)


# License
GPL
