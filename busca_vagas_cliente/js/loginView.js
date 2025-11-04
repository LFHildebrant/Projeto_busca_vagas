import { AuthService } from "./authService.js";
import { Config } from "./main.js";

class LoginView{
    constructor(){
        this.usernameInput = document.getElementById("username");
        this.passwordInput = document.getElementById("password");
        this.messageEl = document.getElementById("message");
        this.formEl = document.getElementById("loginForm");

        this.formEl.addEventListener("submit", this.handleSubmit.bind(this));
    }
    
    async handleSubmit(event){
        event.preventDefault();
        const authService = new AuthService();
        try{
            const result = await authService.login(this.usernameInput.value, this.passwordInput.value);
            let token = result.token;
            if (token){
                localStorage.setItem("token", token);
                
                const userId = Config.getId(token);
                
                localStorage.setItem("id", userId);


                this.messageEl.style.color = "green";
                this.messageEl.textContent = "Login OK. Redirecionando...";
                setTimeout(() => {
                    window.location.href = "user_home.html";
                }, 1000)
                
            }
        } catch (e){
            console.log(e)
        }
    }
}

new LoginView();