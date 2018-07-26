public class RequestHandler {


    public void send_login_status(int id, String login_status){
        Status.getStatus_instance(id).setLogin_status(login_status);
    }

    public void send_shift_status(int id , String shift_status){
        Status.getStatus_instance(id).setShift_status(shift_status);
    }
    public void send_status(int id , String shift_status){
        Status.getStatus_instance(id).setStatus(shift_status);
    }



}
