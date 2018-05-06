

#ifndef CALC_ICE
#define CALC_ICE


module Client
{

 enum Currency {PLN ,GBP ,USD ,EUR};
 enum accountType {STANDARD, PREMIUM};

 struct AccountData {
    int GUID;
    accountType type;
 };

 struct CurrenciesValue {
     float value;
     float value2;
  };

  interface ClientOperation
  {
    AccountData addClient(string name,int PESEL , int monthlyInflows);
    float checkCredit(int PESEL,int GUID,int value,int time);
	int getCredit(int PESEL,int GUID,int value,int time);
	float getBalance(int PESEL,int GUID);
	accountType login(int PESEL , int GUID);
  };

  interface PremiumAccountOperation extends ClientOperation{
	CurrenciesValue checkCreditPremium(int PESEL,int GUID,int value,int time,Currency currency);
	int getCreditPremium(int PESEL,int GUID,int value,int time,Currency currency);
  }
};



#endif