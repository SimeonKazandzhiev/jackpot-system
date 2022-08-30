let stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#betInfo").html("");
}

function connect() {
    const socket = new SockJS('/my-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/game/responses/all', function (allLevels) {
            showAllLevels(JSON.parse(allLevels.body));
        });
        stompClient.subscribe('/user/game/private-responses', function (showInfoDto) {
            showInfo(JSON.parse(showInfoDto.body));
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function sendPrivateBet() {
    stompClient.send("/ws/private-response", {}, JSON.stringify({
        'id': $("#id").val(),
        'amount': $("#amount").val(),
        'playerId': $("#playerId").val(),
        'casinoId': $("#casinoId").val()
    }));
}

function showInfo(showInfoDto) {

    document.getElementById("casinoName").innerText = "Casino: "  + showInfoDto.casinoName;
    document.getElementById("playerIdentity").innerText ="Player id: " + showInfoDto.playerId;
    document.getElementById("playerBalance").innerText ="Player balance: " + showInfoDto.playerBalance;
    document.getElementById("levels").innerText = "Current Levels playing: " + showInfoDto.levelAmounts;
}

function showAllLevels(allLevels) {
    document.getElementById("all-levels").innerText = allLevels;
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#sendBet").click(function () {
        sendPrivateBet();
    });

});