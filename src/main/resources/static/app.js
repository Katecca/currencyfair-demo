var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        $("#connect-status").html("Connected");
        console.log('Connected: ' + frame);
        stompClient.subscribe('/notification/trade-msg', function (msg) {
            var rows = showData(JSON.parse(msg.body));
            $("#trade-msg-list").html(rows);
        })

        //Display existing records at first load
        stompClient.send("/start", {});
    }, function(msg){
        $("#connect-status").html("Disconnected");
    });
}

function showData(msgJson){
    var rows = "";
    for (var i = 0; i < msgJson.length; i++) {

        rows += "<tr>";
        rows += "<td>" + msgJson[i].userId + "</td>";
        rows += "<td>" + msgJson[i].currencyFrom + "</td>";
        rows += "<td>" + msgJson[i].currencyTo + "</td>";
        rows += "<td>" + msgJson[i].amountSell + "</td>";
        rows += "<td>" + msgJson[i].amountBuy + "</td>";
        rows += "<td>" + msgJson[i].rate + "</td>";
        rows += "<td>" + msgJson[i].timePlaced + "</td>";
        rows += "<td>" + msgJson[i].originatingCountry + "</td>";
        rows += "<td>" + new Date(msgJson[i].insertDate).toUTCString() + "</td>";
        rows += "<td>" + msgJson[i].ipAddr + "</td>";
        rows += "</tr>";
    }
    return rows;
}

$(function () {
   $("#connect-status").html("Disconnected"); //Set initial websocket connection status to "Disconnected"
   connect();
});