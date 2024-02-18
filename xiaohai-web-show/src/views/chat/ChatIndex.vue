<script setup lang="ts">
import { ref } from 'vue';
import SockJS from 'sockjs-client/dist/sockjs.min';
import Stomp from 'stompjs';

interface Message {
  content: string;
}

const messages = ref<Message[]>([]);
const newMessage = ref<string>('');

const socket = new WebSocket('ws://localhost:8080/api/gs-guide-websocket'); // WebSocket server URL
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
  stompClient.subscribe('/topic/greetings', (message) => {
    messages.value.push(JSON.parse(message.body));
  });
});

const sendMessage = () => {
  if (newMessage.value.trim() !== '') {
    stompClient.send('/app/hello', {}, JSON.stringify({ name: newMessage.value }));
    newMessage.value = '';
  }
};
</script>

<template>
  <div class="chat-container">
    <div class="chat-messages">
      <div v-for="(message, index) in messages" :key="index" class="message">{{ message }}</div>
    </div>
    <div class="chat-input">
      <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Type a message..." />
      <button @click="sendMessage">Send</button>
    </div>
  </div>
</template>

<style scoped>
.chat-container {
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden;
}

.chat-messages {
  max-height: 300px;
  overflow-y: auto;
}

.message {
  padding: 8px;
  margin: 4px;
  background-color: #f0f0f0;
  border-radius: 5px;
}

.chat-input {
  display: flex;
  align-items: center;
  padding: 8px;
}

.chat-input input {
  flex: 1;
  padding: 6px;
  margin-right: 8px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.chat-input button {
  padding: 6px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
