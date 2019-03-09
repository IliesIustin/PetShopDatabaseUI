# PetShopDatabaseUI
This UI can be used to manage the store of a petShop 

 The database of the app is formed of 6 tables(animale,accesorii,vanzare_animale,vanzare_accesorii,factura,Clienti)
 The relations between tables are : 
 animale - vanzare_animale (many to many)
 accesorii - vanzare_accesorii (many to many)
 vanzare_animale - facturi (one to one)
 vanzare_accesorii - facturi (one to one)
 clienti-facturi (one to many)
 This app is designed for the employees of a pet_shop where they can sell pets or accessories for different clients .
 This app can modify or delete different items in the pet or accessories table or can do some simple and complex selects 
