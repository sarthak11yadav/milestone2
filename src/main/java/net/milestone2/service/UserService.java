package net.milestone2.service;

import net.milestone2.DAO.UserRepo;
import net.milestone2.exception.ResourceNotFoundException;
import net.milestone2.model.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private UserRepo userRepo;



    public UserService(UserRepo userRepository) {
        super();
        this.userRepo = userRepository;
    }

    public boolean userNotExist(User user){

        User findByMobileNo = userRepo.findByMobileno(user.getMobileno());
        User findByEmail = userRepo.findByEmail( user.getEmail());


        if(findByMobileNo==null && findByEmail==null){
            return true;
        }else{
            return false;
        }

    }




    public String saveUser(User user)
    {


        if(userNotExist(user)){
            userRepo.save(user);
            return "Stored";
        }else{
            return "User Already Exist";
        }



    }




    public User getUserById(long id) {
        // TODO Auto-generated method stub
        Optional<User> user=userRepo.findById(id);

        if(user.isPresent()) {
            return user.get();
        }

        else
        {
            throw new ResourceNotFoundException("Not Found");
        }

    }

    public void deleteUser(long id) {
        // TODO Auto-generated method stub
        userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        userRepo.deleteById(id);


    }
    public User findByMobileno(String Mobileno) {
        return userRepo.findByMobileno(Mobileno);
    }


    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public  void updateUser(User user){
        userRepo.save(user);
    }


}
