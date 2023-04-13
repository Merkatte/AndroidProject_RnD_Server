const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/notification', function (message) {
        console.log('Received message: ' + message.body);
    });
});

function sendNotification(userId, message) {
    stompClient.send('/app/notification/' + userId, {}, message);
}