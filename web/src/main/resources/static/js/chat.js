'use strict';
// var usernamePage = document.querySelector('#username-page');
// var chatPage = document.querySelector('#chat-page');
// var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
var host = window.location.host;
var stompClient = null;
var username = '[[${session.username}]]';
var userKey = '[[${session.userKey}]]';
const sendMessageDestination = '/topic/chat.message'; //接受普通消息的订阅地址与发送地址
const stateDestination = '/topic/chat.state';
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

function doConnect() {
    var webSocket = new WebSocket('ws://' + host + '/ws');
    stompClient = Stomp.over(webSocket);
    stompClient.heartbeat.outgoing = 25000; //ping消息发送间隔（空闲的时候发送，在空闲超过24秒就发送ping消息）
    stompClient.heartbeat.incoming = 25000; //接受pong消息最大间隔（空闲的时候，接受服务端pong消息，最大间隔）
    stompClient.connect({username: username}, onConnected, onError);
}

function connect(event) {
    // username = document.querySelector('#name').value.trim();
    // username = username + Math.round(Math.random() * 1000);//尽量产生一个唯一的用户名

    // if (username) {
    //     usernamePage.classList.add('hidden');
    //     chatPage.classList.remove('hidden');
    // }
    doConnect();
    event.preventDefault();
}

window.onload = connect; //页面加载完后执行

function onConnected() {

    // // Subscribe to the Public Topic
    // stompClient.subscribe('/topic/public', onMessageReceived);
    //
    // //额外订阅了mike频道,暂时当做自己的频道,别人需要特意往这个频道发送消息,才能完成 ‘单对单’
    // stompClient.subscribe('/user/topic/msg', onMessageReceived);

    //持久化订阅与队列，订阅的时候增加请求头 {id:321,durable:true,'auto-delete':false}
    //x-queue-name 指定生成的queue名称，而不是由系统随机生成 client-individual手动逐条确认
    stompClient.subscribe(sendMessageDestination, onMessageReceived, {
        'x-queue-name': userKey,
        // ack: 'client-individual',
        durable: true, //持久化订阅 查看 onenote：java-progressive rabbitmq stomp plugin
        'auto-delete': false,
        id: userKey
    });
    //订阅离线，上线消息
    stompClient.subscribe(stateDestination, onMessageReceived, {}); //非持久化消息
    stompClient.send(stateDestination,
        {},
        JSON.stringify({sender: username, type: 'JOIN', to: 'all'})
    );
    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'error ：' + error;
    connectingElement.style.color = 'red';
    console.log("出错啦error:" + error);
    stompClient.disconnect();
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            to: 'all'

        };
        stompClient.send(sendMessageDestination, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 上线~!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' 离线了!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        // payload.ack(); //手动确认消息
    }
    var textElement = document.createElement('p');
    var messageText = document.createTextNode(new Date().Format("yyyy-MM-dd HH:mm:ss") + '：' + message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);