package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.rickandmorty.model.Character
import br.senai.sp.jandira.rickandmorty.model.CharacterList
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.remember as remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var listCharacter = remember {
        mutableListOf(listOf<Character>())
    }

    Column() {
        Button(onClick = {
            //Cria uma chamada para o endpoint
            val call = RetrofitFactory().getCharacterService().getCharacter()

            //Executar a chamada
            call.enqueue(object : Callback<CharacterList>{
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    TODO("Not yet implemented")
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        }) {
            Text(text = "Listar Personagens")
        }
        LazyColumn(){
            items(10){
                Card(
                    backgroundColor = Color.Magenta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = "Espécie do personagem")
                        Text(text = "Nome do personagem")
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}