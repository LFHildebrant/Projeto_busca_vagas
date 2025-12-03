package com.utfpr.Projeto_Sistemas.utilities;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActiveUsersStore {

    private Map<String, LocalDateTime> activeUsers = new ConcurrentHashMap<>();

    public void putUserActivity(String ip){
        this.activeUsers.put(ip, LocalDateTime.now());
    }

    public Map<String, LocalDateTime> getActiveUsers(){
        LocalDateTime limit = LocalDateTime.now().minusMinutes(1);

        activeUsers.entrySet().removeIf(entry -> entry.getValue().isBefore(limit));
        return activeUsers;
    }
}
