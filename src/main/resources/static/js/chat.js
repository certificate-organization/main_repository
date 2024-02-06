//        <script th:inline="javascript">
//            $(document).ready(function(){
//
//            const nickname = [[${#authentication.principal}]];
//
//            $("#disconn").on("click", (e) => {
//                disconnect();
//            })
//
//            $("#button-send").on("click", (e) => {
//                send();
//            });
//
//            const websocket = new WebSocket("ws://localhost:8010/ws/chat");
//
//            websocket.onmessage = onMessage;
//            websocket.onopen = onOpen;
//            websocket.onclose = onClose;
//
//            function send(){
//
//                let msg = document.getElementById("msg");
//
//                console.log(nickname + ":" + msg.value);
//                websocket.send(nickname + ":" + msg.value);
//                msg.value = '';
//            }
//
//            //채팅창에서 나갔을 때
//            function onClose(evt) {
//                var str = nickname + ": 님이 방을 나가셨습니다.";
//                websocket.send(str);
//            }
//
//            //채팅창에 들어왔을 때
//            function onOpen(evt) {
//                var str = nickname + ": 님이 입장하셨습니다.";
//                websocket.send(str);
//            }
//
//            function onMessage(msg) {
//                var data = msg.data;
//                var sessionId = null;
//                //데이터를 보낸 사람
//                var message = null;
//                var arr = data.split(":");
//
//                for(var i=0; i<arr.length; i++){
//                    console.log('arr[' + i + ']: ' + arr[i]);
//                }
//
//                var cur_session = nickname;
//
//                //현재 세션에 로그인 한 사람
//                console.log("cur_session : " + cur_session);
//                sessionId = arr[0];
//                message = arr[1];
//
//                console.log("sessionID : " + sessionId);
//                console.log("cur_session : " + cur_session);
//
//                //로그인 한 클라이언트와 타 클라이언트를 분류하기 위함
//                if(sessionId == cur_session){
//                    var str = "<div class='chat_div'>";
//                    str += "<div class='chat_my_bg'>";
//                    str += "<b>" + sessionId + " : " + message + "</b>";
//                    str += "</div></div>";
//                    $("#msgArea").append(str);
//                }
//                else{
//                    var str = "<div class='chat_div'>";
//                    str += "<div class='chat_you_bg'>";
//                    str += "<b>" + sessionId + " : " + message + "</b>";
//                    str += "</div></div>";
//                     $("#msgArea").append(str);
//                        $("#msgArea").scrollTop($("#msgArea")[0].scrollHeight);
//                        $("#msg").focus();
//                    }
//                      $("#msg").keypress(function(event) {
//                        if (event.which === 13) {
//                            send();
//                        }
//                    });
//            }
//            })
//        </script>