import { Config } from './main.js';

export class AuthService {
  
    constructor(){

    }
    
    async login(username, password) {
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/login");
        console.log("📦 Corpo da requisição:", JSON.stringify({username, password}, null, 2));
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/login", {
            method: "POST",
                body: JSON.stringify( {
                    username, password
                }),
                headers: {
                    "Content-type": "application/json",
                },
                });
                if (response.ok){
                    const data = await response.json();
                    return data;
                } else {
                    const errorData = await response.json().catch(() => null);                    
                    throw new Error("Erro no login: " + JSON.stringify(errorData, null, 2));
                }               
    }

    async logout(token){
        console.log("📤 ENVIANDO requisição POST para:" + sessionStorage.getItem("BASE_URL") + "/logout");
        console.log("🔑 Cabeçalhos:", {
            "Content-type": "application/json",
            "Authorization": `Bearer ${token}`
        });
        const response = await fetch(sessionStorage.getItem("BASE_URL") + "/logout", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });
        if (response.ok){
            return true;
        } else {                
            throw new Error("Erro no logout");
        }    
    }
}