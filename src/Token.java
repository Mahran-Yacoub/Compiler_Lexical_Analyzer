public class Token {

    private String token ;
    private Type typeOfToken ;
    private int id ;

    public Token(String token , Type typeOfToken){
        this.token = token;
        this.typeOfToken = typeOfToken ;
        setId();
    }

    public Token(String token){
        this.token = token;
        setTypeOfToken();
        setId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Type getTypeOfToken() {
        return typeOfToken;
    }

    private void setTypeOfToken() {
        this.typeOfToken = typeOfToken;
    }

    public int getId() {
        return id;
    }

    private void setId() {
        this.id = id;
    }

    @Override
    public String toString() {
        //return "{ Token : "+token+"\t Type : "+ typeOfToken + "\t ID: "+id+" }";
        return token ;
    }
}
