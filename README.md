# ChangeNotifierVMDemo


This project is a proof of concept for a port of Flutter's ChangeNotifierProvider. 

https://pub.dev/documentation/provider/latest/provider/ChangeNotifierProvider-class.html

You make a viewmodel with regalar old fields, extend ChangeNotifierProvider, then whenever you change something call notifyListeners()

    class MainViewModel : ChangeNotifierViewModel() {
        var counter = 0

        fun increment() {
            counter++
            notifyListeners()
        }
    }


To access your vm in a composable, you just do 

    val vm = withChangeNotifier<MainViewModel>()

And then access your fields 


    Text(
        text = "${vm.counter}",
        modifier = Modifier.padding(10.dp))
    Button(onClick = { vm.increment() }) {
        Text("Increment!")
    }

That's it. 

State without fuss. 
