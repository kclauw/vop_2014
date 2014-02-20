package domain;

public enum Privacy 
{
  PUBLIC(0), PRIVATE(1), FRIENDS(2);
  
  private int privacyId;
  
  Privacy(int privacyId)
  {
      this.privacyId = privacyId;
  }
  
  int getPrivacyId()
  {
      return this.privacyId;
  }
  
}
