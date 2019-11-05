import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Native',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Native通信'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _volume = 0;
  int _maxVolume = 0;
  static const MethodChannel _methodChannel =
      const MethodChannel('methodChannel/audio');

  Future<int> getVolume() async {
    try {
      final int result = await _methodChannel.invokeMethod('getVolume');
      return result;
    } catch (e) {}
    return 0;
  }

  @override
  void initState() {
    super.initState();
    getVolume().then((vol) {
      setState(() {
        _volume = vol;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: <Widget>[
          Slider(
            max: 15,
            value: _volume.roundToDouble(),
            onChanged: (double value) {
              print("音量改变  $value");
              var newVolume = value.round();
              _methodChannel.invokeMapMethod(
                "setVolume",
                {'newVolume': newVolume},
              );
              setState(() {
                _volume = newVolume;
              });
            },
          ),
          Text(
            '当前音量 $_volume',
            style: Theme.of(context).textTheme.title,
          ),
        ],
      ),
    );
  }
}
