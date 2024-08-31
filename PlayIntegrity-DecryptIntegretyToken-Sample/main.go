package main

import (
	"context"
	"fmt"
	"log"
	"io/ioutil"
	"net/http"
	"bytes"
	"encoding/json"

	"golang.org/x/oauth2/google"
)

const (
	integrityScope = "https://www.googleapis.com/auth/playintegrity"

	integrityAPIFormat = "https://playintegrity.googleapis.com/v1/%s:decodeIntegrityToken"

	appPackageName = "com.playintergrity.mydevtestapp"

	serviceAccountPath = "play-integrity-1hd42lvpr0s2p46-b1ba510b8d86.json"

	integrityToken = "eyJhbGciOiJBMjU2S1ciLCJlbmMiOiJBMjU2R0NNIn0.VrHUSq7ASQJVHha4LGsQWe2ixXOwsN04ZIr9gUiVVOhSYe5Ltl0zqg.10K8SSL8820UEHGw.H1iMbJSjDa407yGsQcIvknFmMg7ZGXPsqLkCGfxixwuD7X3hCX-9TK2pTG4yDhWRbe-OmZw8RB26UAUAQyxwMI7Bv78CPAULuY1vZ0B-yBDSQX9om4_QYoPTENvfawGEoe_uXT9PdfUtCHvbwrQFrMvq5CV4QvoDODDQQrjs77jtgUZME-tr6HbPRFLsjRDs3QxFenwu4htEkpcP1-s4o76gZD1OeC7i_X9zo2mTk33eECp78mw7-0CcsT-VsDgESXS1Gws6YiIUPXuarwOOayFdZyUd9dHfki4IyGRepnBkLyu5CbWWmDdfCubQZmUgPVuLxAiYZB0v9SCrqc30rDq3NfDZLydLWoSGoExc-F6E_bqz_isekzdrImpy8C41Btk_nHt83iUc7Ixcc2aYBrbnBM5CxPpQP16S4nofHDpY-pbelJAtwX7H6AdG0GhleSHb4dssqJx5lqzicK0HhMA_rZ1ryI1OTJhEUXlLmi773YuMvxDiUIFMEdGc91CSQHL-R6uGoffEFciIj0M9jXT3AyEhktPQDpA-naSK6pw65mWTPDVnt4xNPPTpJ_WsZgEYEHKME9GQfFrQj4fK030dxdZ0GfRCMZLy_mFmnxXdsCxiDNnG842UQr16fh06jWbUTlGuyVeLgDUHIaitqKVJ-yGHyBxn839B1fj4gx_Cyst2UTIUVYXDTTfYEY9EsTlhJ-sQKhTJSqjdFzpSi4QxGJgXevuPWsFWwDGF3UKHIM2Nvc8fsH552_djhZxuJtQWsVYTA_CupxO1eRdV9-VWGXOqs3btrn3LO9MDAjsBRSL0uRqwknfKn23haxzdlw9QwWpbeLgr78xL4Zwn1BqTareQI4EIGfB2sO5SSc4K79sQfkoefFgjxkAZlYNifsm7iK-5InJuOIrtIXhVKd1W-cQhW7RaG8vpuwhE3YpyTANC6_5BRn4ObvcOh57wImVA7sqTWdYMIB814fKZ0J2apJ3j39eSVAtiinS1V2M00ZQT9jtXTTyEXsei4DZcfMZWTLeV-BAyECFBtccB0swkW2VAYTy4sy8usc5T-fsgUCd-70Pn0lFZ7lljlWgdrSen4XIhraUyukXVZxmh5lmZhU9eNQiuYTiEPsRN9auUFR3rH_w5FYvc-q4-az8OPipiieEzBl4W1Gm2sOz4J94ZHiZ1i1-E3uyy3Na43-1n6EEk-wq0nFgfo-zpzg.-pwda0GE2y5IUG3ZajAe9w"
)

func main() {

	// Create a Google OAUTH2 client using Integrity Scope and Google Cloud Service Account
	ctx := context.Background()
	jsonKey, err := ioutil.ReadFile(serviceAccountPath)

	// Read the service account key file.
	key, err := google.JWTConfigFromJSON([]byte(jsonKey), integrityScope)
	if err != nil {
		log.Fatal(err)	
	}
	// Create a new Google Oauth2 Client
	client := key.Client(ctx)

	// Construct the URL format required
	url := fmt.Sprintf(integrityAPIFormat, appPackageName)

	// Construct the JSON body of the message 
	values := map[string]string{"integrity_token": integrityToken}
	requestBody, _ := json.Marshal(values)

	// Create a new HTTP POST request
	request, err := http.NewRequest(http.MethodPost, url, bytes.NewBuffer(requestBody))
	if err != nil {
		fmt.Println("Error when creating request")
		return
	}
	request.WithContext(ctx)

	// Submit requst and get response
	response, err := client.Do(request)
	if err != nil {
		fmt.Println("Error when sending request to the server")
		return
	}

	// Read Response
	responseBody, err := ioutil.ReadAll(response.Body)
	if err != nil {
		log.Fatal(err)
	}

	// log outputs
    fmt.Printf("Client: Request Url:\n%s\n\n", url)
    fmt.Printf("Client: Request Body:\n%s\n\n", requestBody)
    fmt.Printf("Server: Response Body:\n%s\n\n", responseBody)
}
