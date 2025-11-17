import { Config } from "./main.js";

class Connection {
    constructor(){
        this.ip = document.getElementById("ip");
        this.porta = document.getElementById("porta");
        this.formConnection = document.getElementById("connection");
        this.message = document.getElementById("message");

        this.formConnection.addEventListener("submit", this.handleSubmitUpdate.bind(this));

    }
    handleSubmitUpdate(event){
        event.preventDefault();
        let url = "http://" + this.ip.value + ":" + this.porta.value;
        sessionStorage.setItem("BASE_URL", url);
        console.log(Config.BASE_URL);

        setTimeout(() => {
            if (Config.BASE_URL != null){
                window.location.href = "index.html";
            } else {
                this.messageEl.style.color = "red";
                this.messageEl.textContent = "Erro no form";
            }
        }, 1000)

    }
}

new Connection();