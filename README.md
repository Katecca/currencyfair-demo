# Spring boot demo

### Technical stack used in this project
* Spring Boot (Java based)
* H2 Database
* WebSocket
* Gradle

### Approach
1. Two controllers (*MsgFrontendController* & *MsgConsumptionController*) are created to consume the trade message and handle the WebSocket connection respectively.
2. A *TradeMsg* object is created to represent the trade message content.
3. Simple validation is done by using validation annotation offered from Java Bean validation framework. These validations include
    1. **userId** must be > 0
    2. **currencyFrom** & **currencyTo** must be in 3 letters long
    3. **amountSell**, **amountBuy** & **rate** must be > 0
    4. **timePlaced** must be a past date and in dd-MMM-yy k:mm:ss format
    5. **originatingCountry** must be in 2 letters long
    6. All fields are mandatory
4. A validation service with custom exceptions is created for more complex validation. These validations include
    1. **currencyFrom** & **currencyTo** cannot be the same
    2. **currencyRate** must be equal to amountSell/amountBuy
5. BigDecimal data type is used for money and rate related figures
6. JPA repository (*TradeMsgRepository*) is used to persist and query data in/from database
7. H2 is chosen as in-memory database for this project because of its lightweight and easy implementation
8. Simple integration testing is done by SpringBootTest (*BackendDeveloperChallengeApplicationTests*)
9. WebSocket is chosen to reflect up-to-date data
    1. Client will automatically connect to the websocket endpoint (*/ws*)
    2. Client will subscribe to the notification channel (*/notification/trade-msg*)
    3. Client will send a message once connected to server and expect a tradeMsg list will be returned (*/start*)
    4. Client will be notified with a full list of trade messages if a new trade message has been successfully consumed
    5. Client will display the trade message in a table (*/index.html*)

### API
* Endpoint: /data, Method: POST - Trade message consuming endpoint
* Endpoint: /data, Method: DELETE - Remove all trade messages in database 

### Runtime environment
The application is backed by a integrate Tomcat 9 and running in a AWS EC2 instance
