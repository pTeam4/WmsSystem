package service;
//회원 관리
public interface UserService {

    public void addMember();

    public void findId();

    public void findPassword();

    public void modifyMember();

    public void removeMember();

    public void approveAdministrator();

    public void approveMember();

    public void getUnregisteredMembersByVendor();

    public void getMemberDetails();

    public void getMembersByAuthority();

    public void getMembers();

    public void addMemberAuthority();

    public void removeMemberAuthority();

    public void modifyMemberAuthority();

    public void getMemberAuthority();
}
