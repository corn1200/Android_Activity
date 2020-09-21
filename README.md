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

## onCreate()

시스템이 활동을 생성할 때 실행되는 이 콜백을 구현해야 한다. 구현 시 활동의 필수 구성요소를 초기화해야 한다. 예를 들어 앱은 여기에서 뷰를 생성하고 데이터를 목록에 결합해야 한다. 이 콜백에서 setContentView() 를 호출하여 활동의 사용자 인터페이스를 위한 레이아웃을 정의해야 하며 이 작업이 가장 중요합니다.

onCreate() 가 완료되면 다음 콜백은 항상 onStart() 이다.

## onStart()

onCreate() 가 종료되면 활동은 '시작됨' 상태로 전환되고 활동이 사용자에게 표시된다. 이 콜백에는 활동이 포그라운드로 나와서 대화형이 되기 위한 최종 준비에 준하는 작업이 포함된다.

(*포그라운드 작업 : 사용자가 입력한 명령을 해석하여 실행하고 그 결과를 화면에 반영한다. 사용자는 화면에 반영된 결과를 다시 명령을 입력하는 대화형식의 작업이다.)

## onResume()

활동이 사용자와 상호작용을 시작하기 직전에 시스템은 이 콜백을 호출한다. 이 시점에서 활동은 활동 스택의 맨 위에 있으며 모든 사용자 입력을 캡처한다. 앱의 핵심 기능은 대부분 onResume() 메서드로 구현된다.

onPause() 콜백은 항상 onResume() 뒤에 온다.

## onPause()

활동이 포커스를 잃고 '일시중지됨' 상태로 전환될 때 시스템은 onPause() 를 호출한다. 예를 들어 이 상태는 사용자가 뒤로 또는 최근 버튼을 탭할 때 발생한다. 시스템이 활동에서 onPause() 를 호출할 때 이는 엄밀히 말하면 활동이 여전히 부분적으로 표시되지만 대체로 사용자가 활동을 떠나고 있으며 활동이 조만간 '중지됨' 또는 '다시 시작됨' 상태로 전환됨을 나타낸다.

사용자가 UI 업데이트를 기다리고 있다면 '일시중지됨' 상태의 활동은 계속 UI를 업데이트할 수 있다. 이러한 활동의 예에는 내비게이션 지도 화면 또는 미디어 플레이어 재생을 표시하는 활동이 포함된다.

애플리케이션 또는 사용자 데이터를 저장하거나 네트워크를 호출하거나 데이터베이스 트랜잭션을 실행하는 데 onPause() 를 사용해서는 안 된다.

onPause() 가 실행을 완료하면 다음 콜백은 활동이 '일시중지됨' 상태로 전환된 후 발생하는 상황에 따라 onStop() 또는 onResume() 이다.

## onStop()

활동이 사용자에게 더 이상 표시되지 않을 때 시스템은 onStop() 을 호출한다. 이는 활동이 제거 중이거나 새 활동이 시작 중이거나 기존 활동이 '다시 시작됨' 상태로 전환 중이고 중지된 활동을 다루고 있기 때문에 발생할 수 있다. 이 모든 상황에서 중지된 활동은 더 이상 표시되지 않는다.

시스템이 호출하는 다음 콜백은 활동이 사용자와 상호작용하기 위해 다시 시작되면 onRestart() 이며 이 활동이 완전히 종료되면 onDestroy() 이다.

## onRestart()

'중지됨' 상태의 활동이 다시 시작되려고 할 때 시스템은 이 콜백을 호출한다. onRestart() 는 활동이 중지된 시간부터 활동 사태를 복원한다.

이 콜백 뒤에 항상 onStart() 가 온다.

## onDestroy()

시스템은 활동이 제거되기 전에 이 콜백을 호출한다.

이 콜백은 활동이 수신하는 마지막 콜백이다. onDestroy() 는 일반적으로 활동 또는 활동이 포함된 프로세스가 제거될 때 활동의 모든 리소스를 해제하도록 구현된다.

---

# 활동 수명 주기에 관한 이해

사용자가 앱을 탐색하고, 나가고, 다시 돌아가면, 앱의 Activity 인스턴스는 수명 주기 안에서 서로 다른 상태를 통해 전환된다. Activity 클래스는 활동이 상태 변화(시스템이 활동을 생성, 중단 또는 다시 시작하거나, 활동이 있는 프로세스를 종료하는 등)를 알아차릴 수 있는 여러 콜백을 제공한다.

사용자가 활동을 벗어났다가 다시 돌아왔을 때 활동이 작동하는 방식을 수명 주기 콜백 메서드에서 선언할 수 있다. 예를 들어 동영상 스트리밍 플레이어를 빌드하는 경우, 사용자가 다른 앱으로 전환할 때 동영상을 일시중지하고 네트워크 연결을 종료할 수 있다. 사용자가 돌아오면 네트워크를 다시 연결하고, 사용자가 일시중지한 지점에서 동영상을 다시 시작하도록 행동을 취한다. 즉, 각 콜백은 상태 변화에 적합한 특정 작업을 실행할 수 있도록 한다. 적시에 알맞은 작업을 하고 적절하게 전환을 처리하면 앱이 더욱 안정적으로 기능 시킨다. 수명 주기 콜백을 알맞게 구현하면 앱에서 다음과 같은 문제가 발생하지 않도록 예방하는 데 도움이 될 수 있다.

- 사용자가 앱을 사용하는 도중에 전화가 걸려오거나 다른 앱으로 전환할 때 비정상 종료되는 문제

- 사용자가 앱을 활발하게 사용하지 않는 경우에도 시스템 리소스가 지속적으로 소비되는 문제

- 사용자가 앱에서 나갔다가 나중에 돌아왔을 때 사용자의 작업 진행 상태가 저장되지 않는 문제

- 화면이 회전하거나 비정상 종료될 때 사용자의 진행 상태가 저장되지 않는 문제

# 1. 활동 수명 주기 개념

활동 수명 주기 단계 간에 전환하기 위해 활동 클래스는 6가지 콜백으로 구성된 핵심 집합의 onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroy() 를 제공한다. 활동이 새로운 상태에 들어가면 시스템은 각 콜백을 호출한다.

사용자가 활동을 벗어나기 시작하면 시스템은 활동을 해체할 메서드를 호출한다. 어떤 경우엔 부분적으로만 해체하기도 한다. 이때 활동은 여전히 메모리 안에 남아 있으며(예: 사용자가 다른 앱으로 전환하는 경우) 포그라운드로 다시 돌아올 수 있다. 사용자 해당 활동으로 돌아오는 경우 사용자가 종료한 지점에서 활동이 다시 시작된다. 몇 가지 예외를 제외하고 앱은 백그라운드에서 실행될 때 활동을 실행할 수 없다.

(* 포그라운드는 간단하게 시스템과 사용자가 서로 대화를 주고받는 형식을 설명한다. 사용자는 명령하고 시스템은 해석하고 화면에 반영한다. 사용자는 화면에 반영된 내용을 확인하고 다시 명령하는걸 반복하는 형식)

시스템은 그 시점의 활동 상태에 따라 특정 프로세스와 그 안의 활동을 함께 종료할지 여부를 결정한다. 활동 상태 및 메모리에서 제거는 활동 상태와 제거 취약성과의 관계에 관한 자세한 정보를 제공한다.

활동의 복잡도에 따라, 모든 수명 주기 메서드를 구현할 필요가 없는 경우도 있다. 하지만 각각의 수명 주기 메서드를 이해하고, 사용자가 예상한 대로 앱이 동작하도록 필요한 수명 주기 메서드를 구현하는 것이 중요하다.

# 2. 수명 주기 콜백

이 섹션에서는 활동 수명 주기에 사용하는 콜백 메서드의 개념 및 구현 정보를 제공한다.

setContentView() 를 호출하는 등 일부 작업은 활동 수명 주기 메서드 그 자체에 속해 있다. 그러나 종속적인 구성요소의 작업을 구현하는 코드는 해당 구성요소 안에 넣어야 한다. 이를 위해서는 종속적인 구성요소가 수명 주기를 인식하도록 해야 한다.

## onCreate()

이 콜백은 시스템이 먼저 활동을 생성할 때 실행되는 것으로, 필수적으로 구현해야 한다. 활동이 생성되면 '생성됨' 상태가 된다. onCreate() 메서드에서 활동의 전체 수명 주기 동안 한 번만 발생해야 하는 기본 애플리케이션 시작 로직을 실행한다. 예를 들어 onCreate() 를 구현하면 데이터를 목록에 바인딩하고, 활동을 ViewModel 과 연결하고, 일부 클래스 범위 변수를 인스턴스화할 수도 있다. 이 메서드는 savedInstanceState 매개변수를 수신하는데, 이는 활동의 이전 저장 상태가 포함된 Bundle 객체이다. 이번에 처음 생성된 활동인 경우 Bundle 객체의 값은 null이다.

활동의 수명 주기와 연결된 수명 주기 인식 구성요소가 있다면 이 구성요소는 ON_CREATE 이벤트를 수신한다. 따라서 @OnLifecycleEvent라는 어노테이션이 있는 메서드가 호출되고, 수명 주기 인식 구성요소는 '생성됨' 상태에 필요한 모든 설정 코드를 실행할 수 있게 된다.

XML 파일을 정의하고 setContentView() 에 전달하는 대신, 활동 코드에 새로운 View 객체를 생성하고 새로운 View 를 ViewGroup 에 넣어서 뷰 계층 구조를 빌드할 수 있다. 그런 다음 루트 ViewGroup 을 setContentView() 에 전달하여 그 레이아웃을 사용한다.

활동은 '생성됨' 상태에 머무르지 않는다. onCreate() 메서드가 실행을 완료하면 '시작됨' 상태가 되고, 시스템이 연달아 onStart() 와 onResume() 메서드를 호출한다.

## onStart()

활동이 '시작됨' 상태에 들어가면 시스템은 이 콜백을 호출한다. onStart() 가 호출되면 활동이 사용자에게 표시되고, 앱은 활동을 포그라운드(*'활동 수명 주기 개념' 참조)에 보내 상호작용할 수 있도록 준비한다. 예를 들어 이 메서드에서 앱이 UI를 관리하는 코드를 초기화한다.

활동이 '시작됨' 상태로 전환하면 이 활동의 수명 주기와 연결된 모든 수명 주기 인식 구성요소는 ON_START 이벤트를 수신한다.

onStart() 메서드는 매우 빠르게 완료되고, '생성됨' 상태와 마찬가지로 활동은 시작됨 상태에 머무르지 않는다. 이 콜백이 완료되면 활동이 '재개됨' 상태에 들어가고, 시스템이 onResume() 메서드를 호출한다.

## onResume()

활동이 '재개됨' 상태에 들어가면 포그라운드에 표시되고 시스템이 onResume() 콜백을 호출한다. 이 상태에 들어갔을 때 앱이 사용자와 상호작용한다. 어떤 이벤트가 발생하여 앱에서 포커스가 떠날 때까지 앱이 이 상태에 머무른다. 예를 들어 전화가 오거나, 사용자가 다른 활동으로 이동하거나, 기기 화면이 꺼지는 이벤트가 이에 해당한다.

활동이 '재개됨' 상태로 전환되면 이 활동의 수명 주기와 연결된 모든 수명 주기 인식 구성요소는 ON_RESUME 이벤트를 수신한다. 이 상태에서 수명 주기 구성요소가 포그라운드에서 사용자에게 보이는 동안 실행해야 하는 모든 기능을 활성화할 수 있다.

방해되는 이벤트가 발생하면 활동은 '일시중지됨' 상태에 들어가고, 시스템이 onPause() 콜백을 호출한다.

활동이 '일시중지됨' 상태에서 '재개됨' 상태로 돌아오면 시스템이 onResume() 메서드를 다시 한번 호출한다. 따라서 onResume() 을 구현하여 onPause() 중에 해제하는 구성요소를 초기화하고, 활동이 '재개됨' 상태로 전환될 때마다 필요한 다른 초기화 작업도 수행해야 한다.

멀티 윈도우 모드에서는 활동이 '일시중지됨' 상태에 있더라도 완전히 보일 수 있다. 예를 들어 사용자가 멀티 윈도우 모드에 있을 때 활동을 포함하지 않는 다른 창을 탭하면 활동이 '일시중지됨' 상태로 전환된다. 앱이 '재개됨' 상태(포그라운드에 표시되고 활성화된 상태)인 경우에만 카메라를 활성화하고 싶다면 위의 ON_RESUME 이벤트가 실행된 다음에 카메라를 초기화한다. 활동이 '일시중지됨' 상태이지만 표시되어 있는 동안(예: 멀티 윈도우 모드) 카메라를 활성 상태로 유지하고 싶다면 ON_START 이벤트가 실행된 이후에 카메라를 초기화해야 한다. 그러나 활동이 '일시중지됨' 상태일 떄 카메라를 활성화하면 멀티 윈도우 모드에서 '재개됨' 상태에 있는 다른 앱이 카메라에 액세스하지 못할 수도 있다. 활동이 '일시중지됨' 상태일 떄 카메라를 활성 상태로 유지해야 하는 경우도 있지만, 이 경우 전반적인 사용자 환경이 실질적으로 저하될 수 있다. 이 수명 주기의 경우 언제 멀티 윈도우 환경에서 공유된 시스템 리소스를 제어하는 것이 보다 적절한지 신중하게 생각해야한다.

요약: 기기의 하드웨어나 리소스를 사용할 때 적절한 콜백 구현을 해줘야 사용자의 전반적인 환경 저하를 방지한다.

## onPause()

시스템은 사용자가 활동을 떠나는 것을 나타내는 첫 번째 신호로 이 메서드를 호출한다(해당 활동이 항상 소멸되는 것은 아니다). 활동이 포구라운드에 있지 않게 되었다는 것을 나타낸다(사용자가 멀티 윈도우 모드에 있을 경우에는 여전히 표시 될 수도 있다). onPause() 메서드를 사용하여 Activity 가 '일시중지됨' 상태일 때 계속 실행되어서는 안 되지만 잠시 후 다시 시작할 작업을 일시중지하거나 조정한다. 활동이 이 상태에 들어가는 이유는 여러 가지가 있다.

- 일부 이벤트가 앱 실행을 방해한다. 가장 일반적인 사례

- Android 7.0(API 수준 24) 이상에서는 여러 앱이 멀티 윈도우 모드에서 실행된다. 언제든지 그중 하나의 앱(윈도우)만 포커스를 가질 수 있기 때문에 시스템이 그 외에 모든 다른 앱을 일시중지시킨다.

- 새로운 반투명 활동(예: 대화상자)이 열린다. 활동이 여전히 부분적으로 보이지만 포커스 상태가 아닌 경우에는 '일시중지됨' 상태로 유지된다.

활동이 '일시중지됨' 상태로 전환하면 이 활동의 수명 주기와 연결된 모든 수명 주기 인식 구성요소는 ON_PAUSW 이벤트를 수신한다. 여기에서 수명 주기 구성요소는 구성요소가 포그라운드에 있지 않을 때 실행할 필요가 없는 기능을 모두 정지할 수 있다.

또한 onPause() 메서드를 사용하여 시스템 리소스, 센서 핸들(예: GPS) 또는 활동이 일시중지 중이고 사용자가 필요로 하지 않을 때 배터리 수명에 영향을 미칠 수 있는 모든 리소스를 해제할 수도 있다. 그러나 일시중지된 활동은 멀티 윈도우 모드에서 여전히 완전히 보이는 상태일 수 있다. 그러므로 멀티 윈도우 모드를 더욱 잘 지원하기 위해 UI 관련 리소스와 작업을 완전히 해제하거나 조정할 때는 onPause() 대신 onStop() 을 사용하는 것이 좋다.

onPause() 는 아주 잠깐 실행되므로 저장 작업을 실행하기에는 시간이 부족할 수 있다. 그러므로 onPause() 를 사용하여 애플리케이션 또는 사용자 데이터를 저장하거나, 네트워크 호출을 하거나, 데이터베이스 트랜잭션을 실행해서는 안 된다. 이러한 작업은 메서드 실행이 끝나기 전에 완료되지 못할 수도 있다. 그 대신, 부하가 큰 종료 작업은 onStop() 상태일 때 실행해야 한다.

onPause() 메서드의 실행이 완료되더라도 활동이 '일시중지됨' 상태로 남아 있을 수 있다. 오히려 활동은 다시 시작되거나 사용자에게 완전히 보이지 않게 될 때 까지 이 상태에 머무른다. 활동이 다시 시작되면 시스템은 다시 한번 onResume() 콜백을 호출한다. 활동이 '일시중지됨' 상태에서 '재개됨' 상태로 돌아오면 시스템은 Activity 인스턴스를 메모리에 남겨두고, 시스템이 onResume() 을 호출할 때 인스턴스를 다시 호출한다. 이 시나리오에서는 최상위 상태가 '재개됨' 상태인 콜백 메서드 중에 생성된 구성요소는 다시 초기화할 필요가 없다. 활동이 완전히 보이지 않게 되면 시스템은 onStop() 을 호출한다.

## onStop()

활동이 사용자에게 더 이상 표시되지 않으면 '중단됨' 상태에 들어가고, 시스템은 onStop() 콜백을 호출한다. 예를 들어 새로 시작된 활동이 화면 전체를 차지할 경우에 적용된다. 시스템은 활동의 실행이 완료되어 종료될 시점에 onStop() 을 호출할 수도 있다.

활동이 '중단됨' 상태로 전환하면 이 활동의 수명 주기와 연결된 모든 수명 주기 인식 구성요소는 ON_STOP 이벤트를 수신한다. 여기에서 수명 주기 구성요소는 구성요소가 화면에 보이지 않을 때 실행할 필요가 없는 기능을 모두 정지할 수 있다.

onStop() 메서드에서는 앱이 사용자에게 보이지 않는 동안 앱은 필요하지 않은 리소스를 해제하거나 조정해야 한다. 예를 들어 앱은 애니메이션을 일시중지하거나, 세밀한 위치 업데이트에서 대략적인 위치 업데이트로 전환할 수 있다. onPause() 대신 onStop() 을 사용하면 사용자가 멀티 윈도우 모드에서 활동을 보고 있더라도 UI 관련 작업이 계속 진행된다.

또한 onStop() 을 사용하여 CPU를 비교적 많이 소모하는 종료 작업을 실행해야한다. 예를 들어 정보를 데이터베이스에 저장할 적절한 시기를 찾지 못했다면 onStop() 상태일 때 저장할 수 있다. 다음 예시는 초안 내용을 영구 저장소에 저장하는 onStop() 을 구현한 것이다.

```
@Overrride
protected void onStop() {
    // 슈퍼 클래스의 onStop() 메소드를 호출
    super.onStop();

    // Activity가 멈추었기 때문에 메모리에 데이터 저장
    ContentValues values = new ContentValues();
    values.put(NotePad.Notes.COLUMN_NAME_NOTE, getCurrentNoteText());
    values.put(NotePad.Notes.COLUMN_NAME_TITLE, getCurrentNoteTitle());

    // 정보 업데이트
    asyncQueryHandler.startUpdate (
        mToken,
        null,
        uri,
        values,
        null,
        null
    );
}
```

위의 코드 샘플은 SQLite를 직접 사용한다. 여러분은 대신 SQLite에 대한 추상화 레이어를 제공하는 영구 라이브러리인 Room을 사용해야 한다.

활동이 '중단됨' 상태에 들어가면 Activity 객체는 메모리 안에 머무르게 된다. 이 객체가 모든 상태 및 멤버 정보를 관리하지만 창 관리자와 연결되어 있지는 않다. 활동이 다시 시작되면 이 정보를 다시 호출한다. 최상위 상태가 '재개됨' 상태인 콜백 메서드 중에 생성된 구성요소는 다시 초기화할 필요가 없다. 또한 시스템은 레이아웃에 있는 각 View 객체의 현재 상태도 기록한다. 따라서 사용자가 EditText 위젯에 텍스트를 입력하면 해당 내용이 저장되기 때문에 이를 저장 및 복원할 필요가 없다.

```
참고: 활동이 중단되면 시스템은 해당 활동이 포함된 프로세스를 소멸시킬 수 있다(시스템이 메모리를 복구해야 하는 경우). 활동이 중단된 동안 시스템이 프로세스를 소멸시키더라도 Bundle(키-값 쌍의 blob)에 있는 View 객체(예: EditText 위젯의 텍스트) 상태가 그대로 유지되고, 사용자가 이 활동으로 돌아오면 이를 복원한다.
```

활동은 '정지됨' 상태에서 다시 시작되어 사용자와 상호작용하거나, 실행을 종료하고 사라진다. 활동이 다시 시작되면 시스템은 onRestart() 를 호출한다. Activity 가 실행을 종료하면 시스템은 onDestroy() 를 호출한다.