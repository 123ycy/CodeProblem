/**
 * @ClassName: Singleton
 * @Description: g
 *
 * 单例的实现要点
 * 单例模式要求类能够有返回对象一个引用（永远是同一个）和一个获得该实例的方法（必须是静态方法）。
 *
 * 单例的实现主要是通过以下三个步骤：
 * 将类的构造方法定义为私有方法。这样其他类的代码就无法通过调用该类的构造方法来实例化该类的对象，只能通过该类提供的静态方法来得到该类的唯一实例。
 * 定义一个私有的类的静态实例。
 * 提供一个公有的获取实例的静态方法。
 *
 *
 * 单例追求的目标
 * 线程安全。
 * 懒加载。
 * 调用效率高（减少加锁即同步）。
 *
 * @Author: zhaooo
 * @Date: 2022/8/4/12:33
 */

/**
 * 饿汉式
 * 在类加载的时候就对实例进行初始化，没有线程安全问题；获取实例的静态方法没有使用同步，调用效率高；
 * 但是没有使用懒加载，如果该实例从始至终都没被使用过，则会造成内存浪费。
 */
//public class Singleton {
//    private static Singleton instance = new Singleton();
//
//    private Singleton() {
//    }
//
//    private static Singleton getInstance() {
//        return instance;
//    }
//}

/**
 * 懒汉式
 * 在第一次使用的时候才进行初始化，达到了懒加载的效果；
 * 由于获取实例的静态方法用synchronized修饰，所以也没有线程安全的问题；
 * 但是，这种写法每次获取实例都要进行同步（加锁），因此效率较低，并且可能很多同步都是没必要的。
 */
//public class Singleton {
//    private static Singleton instance;
//
//    private Singleton() {
//    }
//
//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }
//}

/**
 * 双重检查
 * 在第一次使用的时候才进行初始化，达到了懒加载的效果；在进行初始化的时候会进行同步（加锁），因此没有线程安全问题；
 * 并且只有第一次进行初始化才进行同步，因此不会有效率方面的问题。
 * 多线程时，为了防止这个过程的重排序，我们需要将变量设置为volatile类型的变量。
 */
//public class Singleton {
//    private static volatile Singleton instance;
//
//    private Singleton() {
//    }
//
//    public static Singleton getInstance() {
//        if (instance == null) {
//            synchronized (Singleton.class) {
//                if (instance == null) {
//                    instance = new Singleton();
//                }
//            }
//        }
//        return instance;
//    }
//}

/**
 * 静态内部类
 * JVM的类加载是懒加载，直到使用这个类时才加载该类，通过一个静态初始化来初始化Singleton，不需要额外的同步。
 * 当任何一个线程第一次调用getInstance时，都会使SingletonHolder被加载和被初始化，此时静态初始化器将执行Singleton的初始化操作。
 */
//public class Singleton {
//
//    private static class SingletonHolder {
//        public static final Singleton INSTANCE = new Singleton();
//    }
//
//    private Singleton() {
//    }
//
//    public Singleton getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//}

/**
 * 枚举
 * 很简洁的一种实现方式，提供了序列化机制，保证线程安全，绝对防止多次实例化，即使是在面对复杂的序列化或者反射攻击的时候。
 * 单例序列化与反序列化前后的对象不是同一个  需要重写readResolve方法返回单例
 */
public enum Singleton {
    INSTANCE;
    // other methods
}