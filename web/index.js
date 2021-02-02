//A1   A2   A3   B1   B2   C1   C2
//var webSocketPorts = [8094,8095,8096,8097,8098,8099,8100];
var webSocketPorts = [8094, 8095, 8096,8097,8098,8099,8100];

var URL = "ws://localhost:"
var socket = [];

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function blink(id, value) {
    var cell = document.getElementById(id);
    cell.innerText = value;
    cell.style.color = "green";
    await sleep(2000);
    cell.style.color = "black";
}

//data.type + data.element es el id
//console.log(data.value); //el valor nuevo
for (var i = 0; i < 7; i++ ){
    socket.push(new WebSocket(URL + webSocketPorts[i]));
    socket[i].onmessage = function (event) {
        var data = JSON.parse(event.data);
        console.log("Node: " + data.type + " Updating: " + data.element + " with value:" + data.value);
        blink("" + data.type + data.element, data.value);
    }
}
