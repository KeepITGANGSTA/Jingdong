package manager;

import android.database.sqlite.SQLiteDatabase;

import com.bwie.jingdong.App;
import com.usher.greendao_demo.greendao.gen.DaoMaster;
import com.usher.greendao_demo.greendao.gen.DaoSession;



/**
 * Created by 李英杰 on 2017/9/17.
 */

public class GreenDaoManager {

    private static DaoMaster.DevOpenHelper helper;
    private static SQLiteDatabase db;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    public static void initDatabase(){
        helper=new DaoMaster.DevOpenHelper(App.context,"JdUserInfo-db",null);
        db=helper.getWritableDatabase();
        mDaoMaster=new DaoMaster(db);
        mDaoSession=mDaoMaster.newSession();
    }

    public static DaoSession getmDaoSession(){
        return mDaoSession;
    }
    public static SQLiteDatabase getDb(){
        return db;
    }
}
