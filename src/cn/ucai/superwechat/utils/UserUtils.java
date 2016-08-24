package cn.ucai.superwechat.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ucai.superwechat.applib.controller.HXSDKHelper;
import cn.ucai.superwechat.DemoHXSDKHelper;
import cn.ucai.superwechat.R;
import cn.ucai.superwechat.domain.User;
import com.squareup.picasso.Picasso;

import java.io.File;

public class UserUtils {
    /**
     * 根据username获取相应user，由于demo没有真实的用户数据，这里给的模拟的数据；
     * @param username
     *
     */
    public static User getUserInfo(String username){
        User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getContactList().get(username);
        if(user == null){
            user = new User(username);
        }
            
        if(user != null){
            //demo没有这些数据，临时填充
        	if(TextUtils.isEmpty(user.getNick()))
        		user.setNick(username);
        }
        return user;
    }
    
    /**
     * 设置用户头像
     * @param username
     */
    public static void setUserAvatar(Context context, String username, ImageView imageView){
    	User user = getUserInfo(username);
        if(user != null && user.getAvatar() != null){
            Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
        }else{
            Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
        }
    }
    
    /**
     * 设置当前用户头像
     */
	public static void setCurrentUserAvatar(Context context, ImageView imageView) {
		User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
		if (user != null && user.getAvatar() != null) {
			Picasso.with(context).load(user.getAvatar()).placeholder(R.drawable.default_avatar).into(imageView);
		} else {
			Picasso.with(context).load(R.drawable.default_avatar).into(imageView);
		}
	}
    
    /**
     * 设置用户昵称
     */
    public static void setUserNick(String username,TextView textView){
    	User user = getUserInfo(username);
    	if(user != null){
    		textView.setText(user.getNick());
    	}else{
    		textView.setText(username);
    	}
    }
    
    /**
     * 设置当前用户昵称
     */
    public static void setCurrentUserNick(TextView textView){
    	User user = ((DemoHXSDKHelper)HXSDKHelper.getInstance()).getUserProfileManager().getCurrentUserInfo();
    	if(textView != null){
    		textView.setText(user.getNick());
    	}
    }
    
    /**
     * 保存或更新某个用户
     *
     */
	public static void saveUserInfo(User newUser) {
		if (newUser == null || newUser.getUsername() == null) {
			return;
		}
		((DemoHXSDKHelper) HXSDKHelper.getInstance()).saveContact(newUser);
	}

	public static class FileUtils {

        /**
         * 获取sd卡的保存位置
         * @param path:http:/10.0.2.2/images/aa.jpg
         */
        public static String getDir(Context context,String path) {
    //		File dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            path=dir.getAbsolutePath()+"/"+path;
            return path;
        }

        /**
         * 修改本地缓存的图片名称
         * @param context
         * @param oldImgName
         * @param newImgName
         */
        public static void renameImageFileName(Context context,String oldImgName,String newImgName){
            String dir = getDir(context, oldImgName);
            File oldFile=new File(dir);
            dir=getDir(context, newImgName);
            File newFile=new File(dir);
            oldFile.renameTo(newFile);
        }

        /**
         * 返回头像的路径
         * @param avatrType：头像的类型，user_avatar：用户头像，group_icon：群组logo
         * @param fielName：头像的文件名，如a.jpg
         * @return
         */
        public static File getAvatarPath(Activity activity, String avatrType, String fielName) {
            File dir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
    //        File dir =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            dir = new File(dir, avatrType);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, fielName);
            return file;
        }
    }
}
