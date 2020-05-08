# FPNN Android SDK

## Depends

* [msgpack-java](https://github.com/msgpack/msgpack-java)
* [spongycastle](https://github.com/rtyley/spongycastle)

### Min api:

android 4.4(api 19)

## Usage

### For Maven Users:

	<dependency>
	  <groupId>org.highras</groupId>
	  <artifactId>fpnn</artifactId>
	  <version>2.0.0-RELEASE</version>
	</dependency>

### For Gradle Users:
implementation 'org.highras:fpnn:2.0.0'

### Import package

	import com.fpnn.sdk.*;
	import com.fpnn.sdk.proto.Answer;
	import com.fpnn.sdk.proto.Quest;


### Create TCPClient

	TCPClient client = new TCPClient(String host, int port);
	TCPClient client = new TCPClient(String host, int port, boolean autoConnect);

### Configure (Optional)

#### Set Duplex Mode (Server Push) (Optional)

	client.setQuestProcessor(Object questProcessor, String questProcessorFullClassName);

#### Set connection events' callbacks (Optional)

	client.setConnectedCallback(ConnectionConnectedCallback cb);
	client.setWillCloseCallback(ConnectionWillCloseCallback cb);

	public interface ConnectionConnectedCallback {
	    void connectResult(InetSocketAddress peerAddress, boolean connected);
	}

	public interface ConnectionWillCloseCallback {
	    void connectionWillClose(InetSocketAddress peerAddress, boolean causedByError);
	}

#### Set Encryption (Optional)

	public boolean client.enableEncryptorByDerFile(String curve, String keyFilePath);
	public boolean client.enableEncryptorByDerData(String curve, byte[] peerPublicKey);

### Send Quest

	//-- Sync method
	public Answer client.sendQuest(Quest quest) throws InterruptedException;
	public Answer client.sendQuest(Quest quest, int timeoutInSeconds) throws InterruptedException;

	//-- Async methods
	public void client.sendQuest(Quest quest, AnswerCallback callback);
	public void client.sendQuest(Quest quest, AnswerCallback callback, int timeoutInSeconds);
	public void client.sendQuest(Quest quest, FunctionalAnswerCallback callback);
	public void client.sendQuest(Quest quest, FunctionalAnswerCallback callback, int timeoutInSeconds);

	public interface FunctionalAnswerCallback {
	    void onAnswer(Answer answer, int errorCode);
	}


### Close (Optional)

	client.close();


### SDK Version

	System.out.println(ClientEngine.SDKVersion);

## API docs

Please refer: [API docs](API.md)


## Directory structure

  * **\<fpnn-sdk-android\>/app**

	Example app for using this SDK.  
	Testing server is \<fpnn\>/core/test/serverTest. Refer: [Cpp codes of serverTest](https://github.com/highras/fpnn/blob/master/core/test/serverTest.cpp)

	[more detail example ref](https://github.com/highras/fpnn-sdk-java/tree/master/examples)

* **\<fpnn-sdk-android\>/fpnnsdk**
	* android moudle.Codes of SDK.
