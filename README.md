# Android_Activity

# 1. 활동 소개

Android에는 main으로써 애플리케이션을 실행하기 위해 반드시 거쳐야할 지점이 있는 것이 아닌 클라이언트의 행동에 따라 콜백 메서드를 호출하고 Activity(간단하게 화면)를 실행하는 것이다.

# 2. 활동의 개념

모바일 앱 환경은 데스크톱 앱 환경과 다르다.

사용자와 앱의 상호작용이 항상 동일한 위치에서 시작되지 않는다.
예를 들어 홈 화면에서 이메일 앱을 열면 이메일 목록이 표시될 수 있고 소셜 미디어 앱을 통해 실행하면 이메일을 작성하기 위한 앱 화면으로 바로 이동할 수 있다.

Activity클래스는 이 패러다임을 촉진하도록 설계되었다.
A앱이 B앱을 호출할 때 A앱은 B앱을 전체적으로 호출하는 것이 아니라 B앱의 활동을 호출한다.
이런 방식으로 활동은 앱과 사용자의 상호작용을 위한 진입점 역할을 한다.

활동의 개념은 앱이 UI를 그리는 창을 제공하는 것이다.
이 창은 일반적으로 화면을 채우지만 화면보다 작고 다른 창 위에 떠 있을 수 있다.
일반적으로 하나의 활동은 앱에서 하나의 화면을 구현한다.

앱의 활동을 사용하려면 앱의 manifest에 활동 관련 정보를 등록하고 활동 수명 주기를 적절히 관리해야 한다.

# 3. manifest 구성

manifest에 활동 및 관련된 특정 속성을 선언해야 앱에서 활동을 사용할 수 있다.(Activity를 만들어도 manifest에 Activity의 정보가 없다면 앱 파일에 생성되지 않는다.)

## 활동 선언

활동을 선언하려면 manifest 파일을 열고 <activity> 요소를 <application> 요소의 하위 요소로 추가해야 한다.

```
<manifest ... >
    <application ... >
        <activity android:name=".MainActivity>
    </application>
</manifest>
```

이 요소의 유일한 필수 속성은 활동의 클래스 이름을 지정하는 android:name이다. 또한 라벨, 아이콘 또는 UI 테마와 같은 활동 특성을 정의하는 속성도 추가할 수 있다.

__* 앱을 게시한 후에는 활동 이름을 변경해서는 안 된다. 활동 이름을 변경하면 앱 바로가기와 같은 일부 기능이 작동하지 않을 수 있다.__

# 4. 인텐트 필터 선언

인텐트 필터는 Android 플랫폼의 매우 강력한 기능이다. 인텐트 필터는 명시적 요청뿐만 아니라 암시적 요청을 기반으로도 활동을 실행하는 기능을 제공한다. 명시적 요청은 지정 앱의 특정 Activity를 시작하도록 시스템에 지시할 수 있다. 반대로 암시적 요청은 특정 작업을 실행해줄 앱의 Activity를 시작하도록 시스템에 지시한다. 시스템 UI에서 사용자에게 작업을 실행할 때 어떤 앱을 사용할지 묻는 메세지가 표시되면 바로 인텐트 필터가 작동한 것이다.

<activity> 요소에서 <intent-filter> 속성을 선언함으로써 이 기능을 활용할 수 있다.
이 요소의 정의에는 <action> 요소와 선택적으로 <category> 요소 또는 <data> 요소가 포함될 수 있다.
이러한 요소를 결합하여 활동이 응답할 수 있는 인텐트 유형을 지정할 수 있다. 예를 들어 텍스트 데이터를 전송하고 다른 활동들의 요청을 수신하는 활동을 구성할 수 있다.

```
<activity android:name=".SendTextActivity">
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="text/plain" />
    </intent-filter>
</activity>
```

<action> 요소는 이 활동이 데이터를 전송하도록 지정한다.
<category> 요소를 DEFAULT로 선언하면 활동이 실행 요청을 수신할 수 있다.
<data> 요소는 이 활동이 전송할 수 있는 데이터 유형을 지정한다.

```
String textMessage = "";
TextView SendText = findViewById(R.id.SendText);
// String 에 전달 받은 메세지를 저장
Intent sendIntent = new Intent();
sendIntent.setAction(Intent.ACTION_SEND);
sendIntent.setType("text/plain");
sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
// View 에 전달 받은 메세지를 set 하고 화면을 보여줌
SendText.setText(textMessage);
startActivity(sendIntent);
```

다른 애플리케이션에서 사용하지 못하게 하려는 활동에는 인텐트 필터가 없어야 하며, 개발자는 명시적 인텐트를 사용하여 활동을 직접 시작할 수 있다.

# 5. 권한 선언

manifest의 <activity> 태그를 사용하여 특정 활동을 시작할 수 있는 앱을 제어할 수 있다. 상위 활동과 하위 활동이 모두 각 manifest에서 동일한 권한을 가지고 있지 않다면 상위 활동이 하위 활동을 실행할 수 없다. 상위 활동에서 <uses-permission> 요소를 선언할 때에는 각 하위 활동에 일치하는 <uses-permission> 요소가 있어야 한다.

예를 들어 앱에서 SocialApp 이라는 가상의 앱을 사용하여 소셜 미디어의 게시물을 공유하려면 다음과 같이 게시물을 호출하는 앱이 보유해야 하는 권한을 SocialApp 자체에서 정의해야 한다

```
<manifest>
<activity android:name="...."
    android:permission="com.google.socialapp.permission.SHARE_POST"
/>
```

다음과 같이 앱의 권한이 SocialApp의 manifest에 설정된 권한과 일치해야 SocialApp을 호출할 수 있습니다.

```
<manifest>
    <uses-permission android:name="com.google.socialapp.permission.SHARE_POST" />
</manifest>
```

# 6. 활동 수명 주기 관리

활동은 수명 주기 전체 기간에 걸쳐 여러 상태를 거친다. 상태 간 전환을 처리하는데 일련의 콜백을 사용할 수 있다.

