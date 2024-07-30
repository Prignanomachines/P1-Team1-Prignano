export class User {
    public userID: number;
    public userName: string;
    public firstName: string;
    public lastName: string;
    public bio: string;

    constructor() {
        this.userID = -1;
        this.userName = "";
        this.firstName = "";
        this.lastName = "";
        this.bio = "";
    }
}

export let user = new User();