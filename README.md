# Тестовое приложения для сбора логов приложения 

## Задача: создать тестовое приложение с одним Activity и библиотеку на Java, которая реализует
* сбор информации об UI-элементах тестового приложения;
* сбор информации о действиях пользователя в рамках Activity;
* сохранение данной информации в background thread сервиса в файл.
* время с последней перезагрузки OS Android
* время установки OS Android на устройство
* дата первого запуска приложения 

### Репозиторий содержит 2 модуля 
  1. `app` содержит тестовое приложения  
  2. `lib_security` содержит библиотеку по сбору логов
  
#### Для запуска сбора логов по дереву view нужно в субклассе  `Application`  выполнить код
    override fun onCreate() {
        super.onCreate()
        SecurityInitialize(this)
    }
    
### Для запуска сбора действий пользователя в activity необходимо зарегистрировать эти действия
#### Создать перехватчик в activity 
    class MainActivity : AppCompatActivity() {
        private val log by lazy { TouchEventInterceptor(this)
    }
#### Для регистрации общих событий необходимо в методе `dispatchTouchEvent` вызвать `log.commonTouch` передав `MotionEvent`
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        log.commonTouch(ev)
        return super.dispatchTouchEvent(ev)
    }
#### Для регистрации конкретных действий вызываем `interceptTouchListener` и `interceptClickListener` передав необходимые параметры 
        // Examples touch interceptor use our the project
        log.interceptTouchListener(btnNext) // specific log on view

        log.interceptTouchListener(emptyContainer, touch = { view, event ->
            //logic touch
            return@interceptTouchListener true
        })

        log.interceptTouchListener(tvFrameTest, nameEvent = "tvFrameTest", touch = { _, _ ->
            // send event touch to children and
            return@interceptTouchListener false
        })
        
        // Examples click interceptor use our the project
        log.interceptClickListener(tvTest1)
        
        log.interceptClickListener(tvTest2, click = {
            // TODO handle click
        })
        log.interceptClickListener(btnTest, "finishClick") {
            this.finish()
        }
#### [Актуальный пример](https://github.com/askont/SecurityTest/tree/master/app)
      
### Структура модуля `lib_security`
* package `log` содержит классы для создания форматированного сообщения и отправляет в очередь для сохранения на устройстве 
* package `service` содержит андроид сервис и dto с типом евента и сообщение которое нужно сохранить 
* package `storage` отвечает за сохранения логов, на данный момент сохранения происходить в файл
* package `utils` служебные классы для формирования структуры логов
* class `SecurityInitialize` необходим для запуска сбора логов

##### путь сохранения `~/android/data/"app package"/logs`
   
 


