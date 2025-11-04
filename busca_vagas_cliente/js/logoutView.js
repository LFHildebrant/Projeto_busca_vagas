import { AuthService } from './authService.js';

class LogoutView{
    constructor(){
        this.message = document.getElementById("message");
        this.logout = document.getElementById("logout");
        this.logout.addEventListener("click", this.handleSubmit.bind(this));
    }

    async handleSubmit(event){
        event.preventDefault();
        const authService = new AuthService();
        try{
            let logoutFeedback = await authService.logout(localStorage.getItem("token"));
            if (logoutFeedback){
                localStorage.removeItem("token");
                this.message.style.color = "black";
                this.message.textContent = "Logout. Redirecionando...";
                setTimeout(() => {
                    window.location.replace("index.html");
                }, 1000)
            }
        } catch (e){
            console.log(e);
        }
    }
}

new LogoutView();
