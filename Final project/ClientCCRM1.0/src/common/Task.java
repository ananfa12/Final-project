package common;

public enum Task {
	// server permissions to client
	CHNGSCRN, LOGOUT, ERROR, CONFIRM, GETLIST, 
	
	// client's requests
	LOGIN, LOGOUT_C, ADDUSER, ADDPROD, ADDSLICE, ADDPATTERN, ADDOP, ADDTYPE, ADDFIELD, DEFTYPE, GETLISTS, GETCOSTLIST, ADDPERMISSON, SENDCOMM, 
	GETCITYLIST, MARKETINGMGRGUI,
	// user screens
	LOGINSCRN, MRKTMGR, MRKTEMP, SALEMAN, COSTREL,
	
	// messages from server
	MSG, MARKETINGEMPLOYEEGUI, SALESMANGUI, ANALYSESYSTEM, ASKREPORTOP, REPORTOP, REPORTUSER, ASKREPORTUSER, ASKFORPRODUCTS, PRODUCTUPDATE, FIELDUPDATE, SLICEUPDATE, ASKFORPRODUCTSEMP, PRODUCTUPDATEEMP, ;
}