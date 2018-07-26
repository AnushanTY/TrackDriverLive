public class Status {
    private String login_status;
    private String shift_status;
    private String status;
    private int id;

    private static Status status_instance;

    private   Status(int id){
        this.id = id;
    }

    public String getLogin_status()
    {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getShift_status() {
        return shift_status;
    }

    public void setShift_status(String shift_status) {
        this.shift_status = shift_status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Status getStatus_instance(int id){                //Multiton, if Process' hashmap has corresponding status to the id it will return the status or create new status
      if(ProcessDriver.getStatus_map().containsKey(id) ) {
          status_instance = ProcessDriver.getStatus_map().get(id) ;

      }
      else{
          status_instance = new Status(id);
          ProcessDriver.getStatus_map().put(id,status_instance);
      }
      return status_instance;
    }

}
