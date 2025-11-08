export class Config{
    static BASE_URL = "http://26.29.111.29:22000";

    static getId(token){
        if (token){
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const decodedPayload = JSON.parse(atob(base64));
            const userId = decodedPayload.userId || decodedPayload.sub; 
            return userId;
        }
    }

    static getRole(token){
        if (token){
            const base64Url = token.split('.')[1];
            const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
            const decodedPayload = JSON.parse(atob(base64));
            const userRole = decodedPayload.userRole || decodedPayload.role; 
            return userRole;
        }
    }
}