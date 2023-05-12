package republicstorage.republicstorage.objects;

public enum QueryType {
    PULL("PULL"),
    DEPOSIT("DEPOSIT"),
    SHOW("SHOW"),
    PATTERN("PATTERN"),
    HELP("HELP"),
    UNDO("UNDO");
    private QueryType(String type){
        this.type = type;
    }
    private String type;
    public String toStr(){
        return type;
    }
}
