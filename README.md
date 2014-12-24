# Aurora

[![Gitter](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gree/aurora?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

AuroraはI/OをシャーディングするためのJava/Scala向けライブラリです。

[![Build Status](https://travis-ci.org/gree/aurora.svg?branch=develop)](https://travis-ci.org/gree/aurora)

## 特徴

- Scala, Javaで利用可能
    - Java版は `aurora-core`、Scala版は `aurora-scala` を提供
    - 他のJVM言語から利用できるはずです
- ストレージデバイスフリー
    - コネクションやSQLなどのストレージデバイス固有の知識に依存しない
    - 標準ではJDBC, Redis, Memcacheなどに対応

## ライセンス

Copyright (c) 2014 GREE, Inc.

MIT License

## サポートする言語

- Java (SE 7以降)
- Scala 2.10.x, 2.11.x

## 機能一覧

- データソース・シャーディング機能  
事前に定義したデータソース群とリゾルバによって、ヒントに応じたデータソースを解決できます(データソースからコネクションの変換は別途ロジックが必要。詳細は後述)。
- テーブル名・シャーディング機能  
事前に定義したリゾルバによって、ヒントに応じたテーブル名を解決できます。


## 使い方(利用者向け)

### データソース・シャーディング機能

#### プロジェクトのひな形を作成する

##### Scalaの場合

- `typesafe-activator`と`sbt`をインストールする。

```sh
$ brew install typesafe-activator sbt
```

- `typesafe-activator` でプロジェクトのひな形を作成する。 `minimal-scala` を選択する。

```sh
$ activator new
Fetching the latest list of templates...

Browse the list of templates: http://typesafe.com/activator/templates
Choose from these featured templates or enter a template name:
  1) minimal-java
  2) minimal-scala
  3) play-java
  4) play-scala
(hit tab to see a list of all templates)
> 2
Enter a name for your application (just press enter for 'minimal-scala')
> example
OK, application "example" is being created using the "minimal-scala" template.

To run "example" from the command-line, run:
/Users/junichi.kato/temp/example/activator run

To run the test for "example" from the command-line, run:
/Users/junichi.kato/temp/example/activator test

To run the Activator UI for "example" from the command-line, run:
/Users/junichi.kato/temp/example/activator ui
```

- `build.sbt` に `aurora-scala` ライブラリへの依存関係を追加する。

```scala
name := """example"""

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq(
	"Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/",
	"Sonatype OSS Snapshots Repository" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies += "net.gree.aurora" %% "aurora-scala" % "x.x.x"
```

- 以下の手順でコンパイルできることを確認する。

```sh
$ cd example
example $ sbt clean compile
```

##### Javaの場合

- Mavenをインストールする

```sh
$ brew install maven
```

- Maven Archetypeでプロジェクトのひな形を生成する。

```sh
$ mvn archetype:generate -DarchetypeArtifactId=maven-archetype-quickstart
[INFO] Scanning for projects...
[INFO]
[INFO] Using the builder org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder with a thread count of 1
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
<snip>
Define value for property 'groupId': : example
Define value for property 'artifactId': : example
Define value for property 'version':  1.0-SNAPSHOT: :
Define value for property 'package':  example: :
Confirm properties configuration:
groupId: example
artifactId: example
version: 1.0-SNAPSHOT
package: example
 Y: : Y
[INFO] ----------------------------------------------------------------------------
[INFO] Using following parameters for creating project from Old (1.x) Archetype: maven-archetype-quickstart:1.0
[INFO] ----------------------------------------------------------------------------
[INFO] Parameter: groupId, Value: example
[INFO] Parameter: packageName, Value: example
[INFO] Parameter: package, Value: example
[INFO] Parameter: artifactId, Value: example
[INFO] Parameter: basedir, Value: /Users/user/
[INFO] Parameter: version, Value: 1.0-SNAPSHOT
[INFO] project created from Old (1.x) Archetype in dir: /Users/user
[INFO] ------------------------------------------------------------------------
```

- `pom.xml` に `aurora-core` ライブラリへの依存関係を追加する。

```xml
<dependencies>
  <dependency>
  	<groupId>net.gree.aurora</groupId>
  	<artifactId>aurora-core</artifactId>
  	<version>x.x.x</version>
  </dependency>
</dependencies>
```

- 以下の手順でコンパイルできることを確認する。

```sh
$ cd example
example $ mvn clean compile
```


#### 設定ファイルを用意する

まず最初に設定ファイル(`conf/application.conf`)を用意します。Auroraは`typesafe config`を利用しています。

例えばJDBCの場合は、以下のように記述します。

```
aurora {
    sharding-configs {
        database {
            type=jdbc
            default {
                driver-class-name="org.mysql.Driver"
                prefix-url="jdbc:mysql://"
                user-name=user1
                password=user1pass
                read-only-user-name=user2
                read-only-password=user2pass
            }
            cluster-groups=[
                {
                    main {
                        clusters=[
                            {
                                cluster1 {
                                    master="192.168.1.2"
                                    slaves=[
                                        "192.168.1.3",
                                        "192.168.1.4"
                                    ]
                                    standby="192.168.1.254"
                                    database="test_a"
                                }
                            },
                            {
                                cluster2 {
                                    master="192.168.2.2"
                                    slaves=[
                                        "192.168.2.3",
                                        "192.168.2.4"
                                    ]
                                    standby="192.168.2.254"
                                    database="test_b"
                                }
                            }
                        ]
                    }
                },
                {
                    sub {
                        clusters=[
                            {
                                cluster1 {
                                    master="192.168.3.2"
                                    slaves=[
                                        "192.168.3.3",
                                        "192.168.3.4"
                                    ]
                                    standby="192.168.3.254"
                                    database="test_a"
                                }
                            },
                            {
                                cluster2 {
                                    master="192.168.4.2"
                                    slaves=[
                                        "192.168.4.3",
                                        "192.168.4.4"
                                    ]
                                    standby="192.168.4.254"
                                    database="test_b"
                                }
                            }
                        ]
                    }
                }
            ]
        }
    }
}
```

`aurora/sharding-configs/database`の部分は、`sharding-config-id`と呼び、開発者が自由に命名できます。

JDBCの場合は`type=jdbc`としてください。`type`は必須項目です(他にも`genric`, `redis`などが使えます。後述)

`default`には、各データソースで共通な設定項目(ドライバクラス名やユーザ名など)を記述します。`driver-class-name`, `prefix-url`, `user-name`, `password`, `read-only-user-name`, `read-only-password`は必須項目です。

<table>
<thead>
	<td>項目名</td><td>意味</td><td>補足</td>
</thead>
<tr>
	<td>driver-class-name</td><td>JDBCドライバクラス名</td><td></td>
</tr>
<tr>
	<td>prefix-url</td><td>JDBC接続URLのプレフィックス</td><td></td>
</tr>
<tr>
	<td>user-name</td><td>JDBC接続ユーザ名</td><td>マスターデータソースに利用します</td>
</tr>
<tr>
	<td>password</td><td>JDBC接続ユーザのパスワード</td><td></td>
</tr>
<tr>
	<td>read-only-user-name</td><td>JDBC接続ユーザ名(読込み専用)</td><td>スレーブデータソースに利用します</td>
</tr>
<tr>
	<td>read-only-password</td><td>JDBC接続ユーザ(読込み専用)のパスワード</td><td></td>
</tr>
</table>

続く、`cluster-groups`(クラスターグループ)には、後述する`cluster`(クラスター)を複数個 登録します(1個でもよい)。クラスターグループは機能ごとに分割したい時に便利です。
`aurora/sharding-configs/{sharding-config-id}/cluster-groups/(main|sub)`の部分は、`cluster-group-id`と呼び、開発者が自由に命名できます。この二つのIDはデータソースを選択する際に必要になります。
`aurora/sharding-config/{sharding-config-id}/cluster-groups/{cluster-groupo-id}/clusters/(cluster1|cluster2)`の部分は、`cluster-id`と呼び、開発者が自由に命名できます。

続く、`cluster`(クラスター)には、マスター用途(`master`)とスレーブ用途(`slave`)のデータソースを定義できます。1クラスターにマスターは1台とし、スレーブは複数台を登録します。`database`にはデータベース名を指定します。(`standby`はコメントです)


### ヒントを与えてデータソースを解決する

ヒントを受け取ってクラスタ名を返すリゾルバ(`clusterIdResolver`)を実装し、AuroraShardingServiceを初期化します(`auroraShardingService`)。
`AuroraShardingService#resolveClusterByHint`メソッドを使って、ヒントに応じたクラスターを取得します。

クラスターにはデータソースが含まれているので、それを使って目的に応じてI/Oを行います(データソースからコネクションの変換はアプリケーション開発者が行う必要があります)

#### Javaの場合

```java
package net.gree.aurora;

import net.gree.aurora.application.AuroraShardingService;
import net.gree.aurora.application.AuroraShardingServiceFactory;
import net.gree.aurora.domain.cluster.AbstractClusterIdResolver;
import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.cluster.ClusterIdResolver;
import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.datasource.DataSourceSelector;
import net.gree.aurora.domain.datasource.JDBCDataSource;
import net.gree.aurora.domain.hint.Hint;
import net.gree.aurora.domain.hint.HintFactory;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class Main {

    private static class User {
        private final int id;
        private final String name;

        User(int id, String name){
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    // リゾルバを定義する
    private final ClusterIdResolver<Integer> clusterIdResolver = new AbstractClusterIdResolver<Integer>("cluster") {

        @Override
        protected String getSuffixName(Hint<Integer> userIdHint, int clusterSize) {
            return Integer.toString(((userIdHint.getValue() +1) % clusterSize) + 1);
        }
        
    };

    // AuroraShardingServiceを初期化する
    private final AuroraShardingService<Integer> auroraShardingService = AuroraShardingServiceFactory.create(clusterIdResolver, new File("./conf/example.conf"));

    private final DataSourceRepository dataSourceRepository = auroraShardingService.getDataSourceRepository().get();

    private User findByUserId(int userId) throws SQLException, ClassNotFoundException, IOException {
        // sharding-config-idとcluster-group-idとヒントを引数に与えてclusterを解決する
        Cluster cluster = auroraShardingService.resolveClusterByHint("database", "main", HintFactory.create(userId)).get();

        // スレーブはランダム選択
        DataSourceSelector<Integer> selector = cluster.createDataSourceSelectorAsRandom(dataSourceRepository);
        // スレーブデータソースを選択する
        JDBCDataSource dataSource = selector.selectDataSourceAsJDBC(null).get();
        // 書き込み用途でマスターを利用する場合は、以下。
        // JDBCDataSource dataSource = cluster.masterDataSource(dataSourceRepository).get();

        System.out.println(dataSource);

        // データソースからコネクションを取得する
        Connection connection = getConnection(dataSource);

        // コネクションを使ってSQLを発行する
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM user WHERE id = " + userId);
        if (rs.next()) {
            return convertToModel(rs);
        }else {
            throw new IOException("entity is not found.");
        }
    }

    // ここでは都度コネクションを取得していますが、
    // 必要に応じてコネクションプールからコネクションを取得してもよいでしょう
    private Connection getConnection(JDBCDataSource dataSource) throws ClassNotFoundException, SQLException {
        Class.forName(dataSource.getDriverClassName());
        return DriverManager.getConnection(dataSource.getUrl(), dataSource.getUserName(), dataSource.getPassword());
    }

    private User convertToModel(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("name"));
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        User user = new Main().findByUserId(1);
        System.out.println(user);
    }

}
```

#### Scalaの場合

```scala
package net.gree.aurora.scala

import java.io.{IOException, File}
import java.sql.{Connection, DriverManager, ResultSet}

import net.gree.aurora.scala.application.AuroraShardingService
import net.gree.aurora.scala.domain.cluster.AbstractClusterIdResolver
import net.gree.aurora.scala.domain.datasource.JDBCDataSource
import net.gree.aurora.scala.domain.hint.Hint
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext

import scala.util.Try

object Main extends App {

  case class User(id: Int, name: String)

  // リゾルバを定義する
  private val clusterIdResolver = new AbstractClusterIdResolver[Int]("cluster") {

    override protected def getSuffixName(userIdHint: Hint[Int], clusterSize: Int): String = {
      (((userIdHint.value + 1) % clusterSize) + 1).toString
    }

  }

  // AuroraShardingServiceを初期化する
  private val auroraShardingService = AuroraShardingService(clusterIdResolver, new File("./conf/example.conf"))

  private implicit val dataSourceRepository = auroraShardingService.dataSourceRepository.get
  private implicit val ctx = SyncEntityIOContext

  private def findByUserId(userId: Int): Try[User] = {
    // sharding-config-idとcluster-group-idとヒントを引数に与えてclusterを解決する
    auroraShardingService.resolveClusterByHint("database", "main", Hint(userId)).map { cluster =>
      // スレーブはランダム選択
      val selector = cluster.createDataSourceSelectorAsRandom
      // スレーブデータソースを選択する
      val dataSource = selector.selectDataSourceAsJDBC().get
      // 書き込み用途でマスターを利用する場合は、以下。
      // val dataSource = cluster.masterDataSource

      println(dataSource)

      // データソースからコネクションを取得する
      val connection = getConnection(dataSource)

      // コネクションを使ってSQLを発行する
      val st = connection.createStatement
      val rs = st.executeQuery("SELECT * FROM user WHERE id = " + userId)
      if (rs.next())
        convertToModel(rs)
      else
         throw new IOException("entity is not found.")
    }
  }

  // ここでは都度コネクションを取得していますが、
  // 必要に応じてコネクションプールからコネクションを取得してもよいでしょう
  private def getConnection(dataSource: JDBCDataSource): Connection = {
    val url = dataSource.url
    val userName = dataSource.userName
    val password = dataSource.password
    Class.forName(dataSource.driverClassName)
    DriverManager.getConnection(url, userName, password)
  }

  private def convertToModel(resultSet: ResultSet): User =
    User(
      id = resultSet.getInt("id"),
      name = resultSet.getString("name")
    )

  val user = findByUserId(1).get

  println(user)

}
```

上記のサンプルコードは、以下の手順で実行することができます(MySQLサーバをlocalhostで稼働させている前提)。

```sh
$ git clone git@github.com:gree/aurora.git
$ cd aurora/tool
$ sh setup_testdb.sh
$ cd ..
$ sbt "project aurora-scala-example" run
```

### その他のデータソースタイプについて

- 対応しているデータソースタイプ
    - 汎用タイプ(generic)
    - JDBC(jdbc)
    - Redis(redis)

### テーブル名・シャーディング機能

TBD

## ビルド手順(コミッタ・コントリビュータ向け)

### ビルドに必要なもの

- Java SE 7

[こちら](http://www.oracle.com/technetwork/java/javase/downloads/index.html)からダウンロードする。

- Scala 2.10.x, 2.11.x

sbtが自動的にダウンロードするのでインストールは不要

- sbt

以下の手順でインストール。
```sh
$ brew install sbt
```

### ソースコードの取得

```sh
$ git clone git@github.com:gree/aurora.git
$ cd aurora
aurora $ git config -l | grep user # 意図どおりか確認する
```

### ビルド手順

```sh
aurora $ sbt clean +package
```

target以下のパスにjarファイルが生成されます。

### IntelliJ IDEA

以下の手順を行い、IDEA上でOpen Projectを行う。

```sh
$ echo 'addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")' >  ~/.sbt/0.13/plugins/build.sbt
aurora $ sbt clean gen-idea
```

### ローカルリポジトリへのデプロイ

```sh
aurora $ sbt clean publish-local
```


### リモートリポジトリ(Sonatype)へのデプロイ方法(コミッタのみの権限)

### 前提

- Sonatype JIRA のアカウントを持っていること。なければ[ここから](https://issues.sonatype.org/secure/Dashboard.jspa)サインアップしてアカウントを取得する。
- Sonatype リポジトリへのデプロイ権限を持っていること。欲しい方は、Issueから申請してください。
- 以下の内容で `~/.ivy2/.credentials` を作成する。

```scala
realm=Sonatype Nexus Repository Manager
host=oss.sonatype.org
user=JIRAアカウント名
password=パスワード
```

- gpgで鍵を作る

鍵を生成する

```sh
$ gpg --gen-key
```

生成された鍵を確認する
    
```sh
$ gpg --list-keys
```

```sh
/home/user1/.gnupg/pubring.gpg
--------------------------------
pub   2038R/7544FE41 2014-06-15
uid                  XXXXX YYYYY <xxxxx@xxxx.xxx>
sub   2038R/FD94AE63 2014-06-15
```

鍵をサーバに送信する

```sh
$ gpg --send-keys 7544FE41
```

#### スナップショット版のデプロイ方法

```sh
aurora $ sbt clean +publish
```

#### リリース版のデプロイ方法

```sh
# リリースブランチを作成する
aurora $ git flow release start v0.0.4
# commonSettingsのversionをあげる
aurora $ vi project/Build.scala
aurora $ sbt clean test
aurora $ git flow release finish v0.0.4
aurora $ git push origin master
aurora $ git push origin develop
aurora $ git push origin refs/tags/v0.0.4
# マスターブランチからデプロイを行う
aurora $ sbt clean
aurora $ sbt +publishSigned
aurora $ sbt +sonatypeRelease
```

## 開発への貢献

Pull Requestをお待ちしております。


## 短縮URL

http://git.io/aurora

